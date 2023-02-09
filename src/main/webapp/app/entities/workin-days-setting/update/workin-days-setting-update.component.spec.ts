import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WorkinDaysSettingFormService } from './workin-days-setting-form.service';
import { WorkinDaysSettingService } from '../service/workin-days-setting.service';
import { IWorkinDaysSetting } from '../workin-days-setting.model';

import { WorkinDaysSettingUpdateComponent } from './workin-days-setting-update.component';

describe('WorkinDaysSetting Management Update Component', () => {
  let comp: WorkinDaysSettingUpdateComponent;
  let fixture: ComponentFixture<WorkinDaysSettingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workinDaysSettingFormService: WorkinDaysSettingFormService;
  let workinDaysSettingService: WorkinDaysSettingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [WorkinDaysSettingUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(WorkinDaysSettingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkinDaysSettingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workinDaysSettingFormService = TestBed.inject(WorkinDaysSettingFormService);
    workinDaysSettingService = TestBed.inject(WorkinDaysSettingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const workinDaysSetting: IWorkinDaysSetting = { id: 456 };

      activatedRoute.data = of({ workinDaysSetting });
      comp.ngOnInit();

      expect(comp.workinDaysSetting).toEqual(workinDaysSetting);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkinDaysSetting>>();
      const workinDaysSetting = { id: 123 };
      jest.spyOn(workinDaysSettingFormService, 'getWorkinDaysSetting').mockReturnValue(workinDaysSetting);
      jest.spyOn(workinDaysSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workinDaysSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workinDaysSetting }));
      saveSubject.complete();

      // THEN
      expect(workinDaysSettingFormService.getWorkinDaysSetting).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workinDaysSettingService.update).toHaveBeenCalledWith(expect.objectContaining(workinDaysSetting));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkinDaysSetting>>();
      const workinDaysSetting = { id: 123 };
      jest.spyOn(workinDaysSettingFormService, 'getWorkinDaysSetting').mockReturnValue({ id: null });
      jest.spyOn(workinDaysSettingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workinDaysSetting: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workinDaysSetting }));
      saveSubject.complete();

      // THEN
      expect(workinDaysSettingFormService.getWorkinDaysSetting).toHaveBeenCalled();
      expect(workinDaysSettingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkinDaysSetting>>();
      const workinDaysSetting = { id: 123 };
      jest.spyOn(workinDaysSettingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workinDaysSetting });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workinDaysSettingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
