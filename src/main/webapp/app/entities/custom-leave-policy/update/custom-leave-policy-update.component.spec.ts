import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CustomLeavePolicyFormService } from './custom-leave-policy-form.service';
import { CustomLeavePolicyService } from '../service/custom-leave-policy.service';
import { ICustomLeavePolicy } from '../custom-leave-policy.model';
import { ILeavePolicy } from 'app/entities/leave-policy/leave-policy.model';
import { LeavePolicyService } from 'app/entities/leave-policy/service/leave-policy.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { CustomLeavePolicyUpdateComponent } from './custom-leave-policy-update.component';

describe('CustomLeavePolicy Management Update Component', () => {
  let comp: CustomLeavePolicyUpdateComponent;
  let fixture: ComponentFixture<CustomLeavePolicyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let customLeavePolicyFormService: CustomLeavePolicyFormService;
  let customLeavePolicyService: CustomLeavePolicyService;
  let leavePolicyService: LeavePolicyService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CustomLeavePolicyUpdateComponent],
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
      .overrideTemplate(CustomLeavePolicyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustomLeavePolicyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    customLeavePolicyFormService = TestBed.inject(CustomLeavePolicyFormService);
    customLeavePolicyService = TestBed.inject(CustomLeavePolicyService);
    leavePolicyService = TestBed.inject(LeavePolicyService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LeavePolicy query and add missing value', () => {
      const customLeavePolicy: ICustomLeavePolicy = { id: 456 };
      const leavePolicy: ILeavePolicy = { id: 57822 };
      customLeavePolicy.leavePolicy = leavePolicy;

      const leavePolicyCollection: ILeavePolicy[] = [{ id: 81082 }];
      jest.spyOn(leavePolicyService, 'query').mockReturnValue(of(new HttpResponse({ body: leavePolicyCollection })));
      const additionalLeavePolicies = [leavePolicy];
      const expectedCollection: ILeavePolicy[] = [...additionalLeavePolicies, ...leavePolicyCollection];
      jest.spyOn(leavePolicyService, 'addLeavePolicyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ customLeavePolicy });
      comp.ngOnInit();

      expect(leavePolicyService.query).toHaveBeenCalled();
      expect(leavePolicyService.addLeavePolicyToCollectionIfMissing).toHaveBeenCalledWith(
        leavePolicyCollection,
        ...additionalLeavePolicies.map(expect.objectContaining)
      );
      expect(comp.leavePoliciesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employee query and add missing value', () => {
      const customLeavePolicy: ICustomLeavePolicy = { id: 456 };
      const employee: IEmployee = { id: 88159 };
      customLeavePolicy.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 99578 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ customLeavePolicy });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const customLeavePolicy: ICustomLeavePolicy = { id: 456 };
      const leavePolicy: ILeavePolicy = { id: 60404 };
      customLeavePolicy.leavePolicy = leavePolicy;
      const employee: IEmployee = { id: 80847 };
      customLeavePolicy.employee = employee;

      activatedRoute.data = of({ customLeavePolicy });
      comp.ngOnInit();

      expect(comp.leavePoliciesSharedCollection).toContain(leavePolicy);
      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.customLeavePolicy).toEqual(customLeavePolicy);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomLeavePolicy>>();
      const customLeavePolicy = { id: 123 };
      jest.spyOn(customLeavePolicyFormService, 'getCustomLeavePolicy').mockReturnValue(customLeavePolicy);
      jest.spyOn(customLeavePolicyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customLeavePolicy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customLeavePolicy }));
      saveSubject.complete();

      // THEN
      expect(customLeavePolicyFormService.getCustomLeavePolicy).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(customLeavePolicyService.update).toHaveBeenCalledWith(expect.objectContaining(customLeavePolicy));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomLeavePolicy>>();
      const customLeavePolicy = { id: 123 };
      jest.spyOn(customLeavePolicyFormService, 'getCustomLeavePolicy').mockReturnValue({ id: null });
      jest.spyOn(customLeavePolicyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customLeavePolicy: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customLeavePolicy }));
      saveSubject.complete();

      // THEN
      expect(customLeavePolicyFormService.getCustomLeavePolicy).toHaveBeenCalled();
      expect(customLeavePolicyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomLeavePolicy>>();
      const customLeavePolicy = { id: 123 };
      jest.spyOn(customLeavePolicyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customLeavePolicy });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(customLeavePolicyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLeavePolicy', () => {
      it('Should forward to leavePolicyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(leavePolicyService, 'compareLeavePolicy');
        comp.compareLeavePolicy(entity, entity2);
        expect(leavePolicyService.compareLeavePolicy).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
