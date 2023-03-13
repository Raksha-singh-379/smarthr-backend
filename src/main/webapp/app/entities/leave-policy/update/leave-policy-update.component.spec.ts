import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LeavePolicyFormService } from './leave-policy-form.service';
import { LeavePolicyService } from '../service/leave-policy.service';
import { ILeavePolicy } from '../leave-policy.model';
import { ILeaveType } from 'app/entities/leave-type/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type/service/leave-type.service';
import { IEmploymentType } from 'app/entities/employment-type/employment-type.model';
import { EmploymentTypeService } from 'app/entities/employment-type/service/employment-type.service';

import { LeavePolicyUpdateComponent } from './leave-policy-update.component';

describe('LeavePolicy Management Update Component', () => {
  let comp: LeavePolicyUpdateComponent;
  let fixture: ComponentFixture<LeavePolicyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let leavePolicyFormService: LeavePolicyFormService;
  let leavePolicyService: LeavePolicyService;
  let leaveTypeService: LeaveTypeService;
  let employmentTypeService: EmploymentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LeavePolicyUpdateComponent],
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
      .overrideTemplate(LeavePolicyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LeavePolicyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    leavePolicyFormService = TestBed.inject(LeavePolicyFormService);
    leavePolicyService = TestBed.inject(LeavePolicyService);
    leaveTypeService = TestBed.inject(LeaveTypeService);
    employmentTypeService = TestBed.inject(EmploymentTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LeaveType query and add missing value', () => {
      const leavePolicy: ILeavePolicy = { id: 456 };
      const leaveType: ILeaveType = { id: 43618 };
      leavePolicy.leaveType = leaveType;

      const leaveTypeCollection: ILeaveType[] = [{ id: 32885 }];
      jest.spyOn(leaveTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: leaveTypeCollection })));
      const additionalLeaveTypes = [leaveType];
      const expectedCollection: ILeaveType[] = [...additionalLeaveTypes, ...leaveTypeCollection];
      jest.spyOn(leaveTypeService, 'addLeaveTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      expect(leaveTypeService.query).toHaveBeenCalled();
      expect(leaveTypeService.addLeaveTypeToCollectionIfMissing).toHaveBeenCalledWith(
        leaveTypeCollection,
        ...additionalLeaveTypes.map(expect.objectContaining)
      );
      expect(comp.leaveTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EmploymentType query and add missing value', () => {
      const leavePolicy: ILeavePolicy = { id: 456 };
      const employmentType: IEmploymentType = { id: 96992 };
      leavePolicy.employmentType = employmentType;

      const employmentTypeCollection: IEmploymentType[] = [{ id: 23690 }];
      jest.spyOn(employmentTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: employmentTypeCollection })));
      const additionalEmploymentTypes = [employmentType];
      const expectedCollection: IEmploymentType[] = [...additionalEmploymentTypes, ...employmentTypeCollection];
      jest.spyOn(employmentTypeService, 'addEmploymentTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      expect(employmentTypeService.query).toHaveBeenCalled();
      expect(employmentTypeService.addEmploymentTypeToCollectionIfMissing).toHaveBeenCalledWith(
        employmentTypeCollection,
        ...additionalEmploymentTypes.map(expect.objectContaining)
      );
      expect(comp.employmentTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const leavePolicy: ILeavePolicy = { id: 456 };
      const leaveType: ILeaveType = { id: 29834 };
      leavePolicy.leaveType = leaveType;
      const employmentType: IEmploymentType = { id: 8833 };
      leavePolicy.employmentType = employmentType;

      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      expect(comp.leaveTypesSharedCollection).toContain(leaveType);
      expect(comp.employmentTypesSharedCollection).toContain(employmentType);
      expect(comp.leavePolicy).toEqual(leavePolicy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeavePolicy>>();
      const leavePolicy = { id: 123 };
      jest.spyOn(leavePolicyFormService, 'getLeavePolicy').mockReturnValue(leavePolicy);
      jest.spyOn(leavePolicyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leavePolicy }));
      saveSubject.complete();

      // THEN
      expect(leavePolicyFormService.getLeavePolicy).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(leavePolicyService.update).toHaveBeenCalledWith(expect.objectContaining(leavePolicy));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeavePolicy>>();
      const leavePolicy = { id: 123 };
      jest.spyOn(leavePolicyFormService, 'getLeavePolicy').mockReturnValue({ id: null });
      jest.spyOn(leavePolicyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leavePolicy: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: leavePolicy }));
      saveSubject.complete();

      // THEN
      expect(leavePolicyFormService.getLeavePolicy).toHaveBeenCalled();
      expect(leavePolicyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILeavePolicy>>();
      const leavePolicy = { id: 123 };
      jest.spyOn(leavePolicyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ leavePolicy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(leavePolicyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLeaveType', () => {
      it('Should forward to leaveTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(leaveTypeService, 'compareLeaveType');
        comp.compareLeaveType(entity, entity2);
        expect(leaveTypeService.compareLeaveType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmploymentType', () => {
      it('Should forward to employmentTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employmentTypeService, 'compareEmploymentType');
        comp.compareEmploymentType(entity, entity2);
        expect(employmentTypeService.compareEmploymentType).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
