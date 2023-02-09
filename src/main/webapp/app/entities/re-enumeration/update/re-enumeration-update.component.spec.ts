import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReEnumerationFormService } from './re-enumeration-form.service';
import { ReEnumerationService } from '../service/re-enumeration.service';
import { IReEnumeration } from '../re-enumeration.model';

import { ReEnumerationUpdateComponent } from './re-enumeration-update.component';

describe('ReEnumeration Management Update Component', () => {
  let comp: ReEnumerationUpdateComponent;
  let fixture: ComponentFixture<ReEnumerationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reEnumerationFormService: ReEnumerationFormService;
  let reEnumerationService: ReEnumerationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReEnumerationUpdateComponent],
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
      .overrideTemplate(ReEnumerationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReEnumerationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reEnumerationFormService = TestBed.inject(ReEnumerationFormService);
    reEnumerationService = TestBed.inject(ReEnumerationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const reEnumeration: IReEnumeration = { id: 456 };

      activatedRoute.data = of({ reEnumeration });
      comp.ngOnInit();

      expect(comp.reEnumeration).toEqual(reEnumeration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReEnumeration>>();
      const reEnumeration = { id: 123 };
      jest.spyOn(reEnumerationFormService, 'getReEnumeration').mockReturnValue(reEnumeration);
      jest.spyOn(reEnumerationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reEnumeration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reEnumeration }));
      saveSubject.complete();

      // THEN
      expect(reEnumerationFormService.getReEnumeration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(reEnumerationService.update).toHaveBeenCalledWith(expect.objectContaining(reEnumeration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReEnumeration>>();
      const reEnumeration = { id: 123 };
      jest.spyOn(reEnumerationFormService, 'getReEnumeration').mockReturnValue({ id: null });
      jest.spyOn(reEnumerationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reEnumeration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: reEnumeration }));
      saveSubject.complete();

      // THEN
      expect(reEnumerationFormService.getReEnumeration).toHaveBeenCalled();
      expect(reEnumerationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IReEnumeration>>();
      const reEnumeration = { id: 123 };
      jest.spyOn(reEnumerationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ reEnumeration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reEnumerationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
