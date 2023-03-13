import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EmployeeLeaveAccountFormService, EmployeeLeaveAccountFormGroup } from './employee-leave-account-form.service';
import { IEmployeeLeaveAccount } from '../employee-leave-account.model';
import { EmployeeLeaveAccountService } from '../service/employee-leave-account.service';
import { ILeaveType } from 'app/entities/leave-type/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type/service/leave-type.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

@Component({
  selector: 'jhi-employee-leave-account-update',
  templateUrl: './employee-leave-account-update.component.html',
})
export class EmployeeLeaveAccountUpdateComponent implements OnInit {
  isSaving = false;
  employeeLeaveAccount: IEmployeeLeaveAccount | null = null;

  leaveTypesSharedCollection: ILeaveType[] = [];
  employeesSharedCollection: IEmployee[] = [];

  editForm: EmployeeLeaveAccountFormGroup = this.employeeLeaveAccountFormService.createEmployeeLeaveAccountFormGroup();

  constructor(
    protected employeeLeaveAccountService: EmployeeLeaveAccountService,
    protected employeeLeaveAccountFormService: EmployeeLeaveAccountFormService,
    protected leaveTypeService: LeaveTypeService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLeaveType = (o1: ILeaveType | null, o2: ILeaveType | null): boolean => this.leaveTypeService.compareLeaveType(o1, o2);

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeLeaveAccount }) => {
      this.employeeLeaveAccount = employeeLeaveAccount;
      if (employeeLeaveAccount) {
        this.updateForm(employeeLeaveAccount);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeLeaveAccount = this.employeeLeaveAccountFormService.getEmployeeLeaveAccount(this.editForm);
    if (employeeLeaveAccount.id !== null) {
      this.subscribeToSaveResponse(this.employeeLeaveAccountService.update(employeeLeaveAccount));
    } else {
      this.subscribeToSaveResponse(this.employeeLeaveAccountService.create(employeeLeaveAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeLeaveAccount>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(employeeLeaveAccount: IEmployeeLeaveAccount): void {
    this.employeeLeaveAccount = employeeLeaveAccount;
    this.employeeLeaveAccountFormService.resetForm(this.editForm, employeeLeaveAccount);

    this.leaveTypesSharedCollection = this.leaveTypeService.addLeaveTypeToCollectionIfMissing<ILeaveType>(
      this.leaveTypesSharedCollection,
      employeeLeaveAccount.leaveType
    );
    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      employeeLeaveAccount.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.leaveTypeService
      .query()
      .pipe(map((res: HttpResponse<ILeaveType[]>) => res.body ?? []))
      .pipe(
        map((leaveTypes: ILeaveType[]) =>
          this.leaveTypeService.addLeaveTypeToCollectionIfMissing<ILeaveType>(leaveTypes, this.employeeLeaveAccount?.leaveType)
        )
      )
      .subscribe((leaveTypes: ILeaveType[]) => (this.leaveTypesSharedCollection = leaveTypes));

    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.employeeLeaveAccount?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
