import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmployeeLeaveAccountFormService } from './employee-leave-account-form.service';
import { EmployeeLeaveAccountService } from '../service/employee-leave-account.service';
import { IEmployeeLeaveAccount } from '../employee-leave-account.model';
import { ILeaveType } from 'app/entities/leave-type/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type/service/leave-type.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { EmployeeLeaveAccountUpdateComponent } from './employee-leave-account-update.component';

describe('EmployeeLeaveAccount Management Update Component', () => {
  let comp: EmployeeLeaveAccountUpdateComponent;
  let fixture: ComponentFixture<EmployeeLeaveAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeLeaveAccountFormService: EmployeeLeaveAccountFormService;
  let employeeLeaveAccountService: EmployeeLeaveAccountService;
  let leaveTypeService: LeaveTypeService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmployeeLeaveAccountUpdateComponent],
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
      .overrideTemplate(EmployeeLeaveAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeLeaveAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeLeaveAccountFormService = TestBed.inject(EmployeeLeaveAccountFormService);
    employeeLeaveAccountService = TestBed.inject(EmployeeLeaveAccountService);
    leaveTypeService = TestBed.inject(LeaveTypeService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LeaveType query and add missing value', () => {
      const employeeLeaveAccount: IEmployeeLeaveAccount = { id: 456 };
      const leaveType: ILeaveType = { id: 90159 };
      employeeLeaveAccount.leaveType = leaveType;

      const leaveTypeCollection: ILeaveType[] = [{ id: 89067 }];
      jest.spyOn(leaveTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: leaveTypeCollection })));
      const additionalLeaveTypes = [leaveType];
      const expectedCollection: ILeaveType[] = [...additionalLeaveTypes, ...leaveTypeCollection];
      jest.spyOn(leaveTypeService, 'addLeaveTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employeeLeaveAccount });
      comp.ngOnInit();

      expect(leaveTypeService.query).toHaveBeenCalled();
      expect(leaveTypeService.addLeaveTypeToCollectionIfMissing).toHaveBeenCalledWith(
        leaveTypeCollection,
        ...additionalLeaveTypes.map(expect.objectContaining)
      );
      expect(comp.leaveTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Employee query and add missing value', () => {
      const employeeLeaveAccount: IEmployeeLeaveAccount = { id: 456 };
      const employee: IEmployee = { id: 96944 };
      employeeLeaveAccount.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 19476 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employeeLeaveAccount });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employeeLeaveAccount: IEmployeeLeaveAccount = { id: 456 };
      const leaveType: ILeaveType = { id: 57984 };
      employeeLeaveAccount.leaveType = leaveType;
      const employee: IEmployee = { id: 51255 };
      employeeLeaveAccount.employee = employee;

      activatedRoute.data = of({ employeeLeaveAccount });
      comp.ngOnInit();

      expect(comp.leaveTypesSharedCollection).toContain(leaveType);
      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.employeeLeaveAccount).toEqual(employeeLeaveAccount);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeLeaveAccount>>();
      const employeeLeaveAccount = { id: 123 };
      jest.spyOn(employeeLeaveAccountFormService, 'getEmployeeLeaveAccount').mockReturnValue(employeeLeaveAccount);
      jest.spyOn(employeeLeaveAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeLeaveAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeLeaveAccount }));
      saveSubject.complete();

      // THEN
      expect(employeeLeaveAccountFormService.getEmployeeLeaveAccount).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeLeaveAccountService.update).toHaveBeenCalledWith(expect.objectContaining(employeeLeaveAccount));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeLeaveAccount>>();
      const employeeLeaveAccount = { id: 123 };
      jest.spyOn(employeeLeaveAccountFormService, 'getEmployeeLeaveAccount').mockReturnValue({ id: null });
      jest.spyOn(employeeLeaveAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeLeaveAccount: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeLeaveAccount }));
      saveSubject.complete();

      // THEN
      expect(employeeLeaveAccountFormService.getEmployeeLeaveAccount).toHaveBeenCalled();
      expect(employeeLeaveAccountService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeLeaveAccount>>();
      const employeeLeaveAccount = { id: 123 };
      jest.spyOn(employeeLeaveAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeLeaveAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeLeaveAccountService.update).toHaveBeenCalled();
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
