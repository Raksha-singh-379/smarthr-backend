import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ApproverFormService } from './approver-form.service';
import { ApproverService } from '../service/approver.service';
import { IApprover } from '../approver.model';

import { ApproverUpdateComponent } from './approver-update.component';

describe('Approver Management Update Component', () => {
  let comp: ApproverUpdateComponent;
  let fixture: ComponentFixture<ApproverUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let approverFormService: ApproverFormService;
  let approverService: ApproverService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ApproverUpdateComponent],
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
      .overrideTemplate(ApproverUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApproverUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    approverFormService = TestBed.inject(ApproverFormService);
    approverService = TestBed.inject(ApproverService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const approver: IApprover = { id: 456 };

      activatedRoute.data = of({ approver });
      comp.ngOnInit();

      expect(comp.approver).toEqual(approver);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApprover>>();
      const approver = { id: 123 };
      jest.spyOn(approverFormService, 'getApprover').mockReturnValue(approver);
      jest.spyOn(approverService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ approver });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: approver }));
      saveSubject.complete();

      // THEN
      expect(approverFormService.getApprover).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(approverService.update).toHaveBeenCalledWith(expect.objectContaining(approver));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApprover>>();
      const approver = { id: 123 };
      jest.spyOn(approverFormService, 'getApprover').mockReturnValue({ id: null });
      jest.spyOn(approverService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ approver: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: approver }));
      saveSubject.complete();

      // THEN
      expect(approverFormService.getApprover).toHaveBeenCalled();
      expect(approverService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IApprover>>();
      const approver = { id: 123 };
      jest.spyOn(approverService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ approver });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(approverService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
