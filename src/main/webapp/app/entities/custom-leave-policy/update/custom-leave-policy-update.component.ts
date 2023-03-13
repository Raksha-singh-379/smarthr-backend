import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CustomLeavePolicyFormService, CustomLeavePolicyFormGroup } from './custom-leave-policy-form.service';
import { ICustomLeavePolicy } from '../custom-leave-policy.model';
import { CustomLeavePolicyService } from '../service/custom-leave-policy.service';
import { ILeavePolicy } from 'app/entities/leave-policy/leave-policy.model';
import { LeavePolicyService } from 'app/entities/leave-policy/service/leave-policy.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

@Component({
  selector: 'jhi-custom-leave-policy-update',
  templateUrl: './custom-leave-policy-update.component.html',
})
export class CustomLeavePolicyUpdateComponent implements OnInit {
  isSaving = false;
  customLeavePolicy: ICustomLeavePolicy | null = null;

  leavePoliciesSharedCollection: ILeavePolicy[] = [];
  employeesSharedCollection: IEmployee[] = [];

  editForm: CustomLeavePolicyFormGroup = this.customLeavePolicyFormService.createCustomLeavePolicyFormGroup();

  constructor(
    protected customLeavePolicyService: CustomLeavePolicyService,
    protected customLeavePolicyFormService: CustomLeavePolicyFormService,
    protected leavePolicyService: LeavePolicyService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLeavePolicy = (o1: ILeavePolicy | null, o2: ILeavePolicy | null): boolean => this.leavePolicyService.compareLeavePolicy(o1, o2);

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customLeavePolicy }) => {
      this.customLeavePolicy = customLeavePolicy;
      if (customLeavePolicy) {
        this.updateForm(customLeavePolicy);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customLeavePolicy = this.customLeavePolicyFormService.getCustomLeavePolicy(this.editForm);
    if (customLeavePolicy.id !== null) {
      this.subscribeToSaveResponse(this.customLeavePolicyService.update(customLeavePolicy));
    } else {
      this.subscribeToSaveResponse(this.customLeavePolicyService.create(customLeavePolicy));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomLeavePolicy>>): void {
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

  protected updateForm(customLeavePolicy: ICustomLeavePolicy): void {
    this.customLeavePolicy = customLeavePolicy;
    this.customLeavePolicyFormService.resetForm(this.editForm, customLeavePolicy);

    this.leavePoliciesSharedCollection = this.leavePolicyService.addLeavePolicyToCollectionIfMissing<ILeavePolicy>(
      this.leavePoliciesSharedCollection,
      customLeavePolicy.leavePolicy
    );
    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      customLeavePolicy.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.leavePolicyService
      .query()
      .pipe(map((res: HttpResponse<ILeavePolicy[]>) => res.body ?? []))
      .pipe(
        map((leavePolicies: ILeavePolicy[]) =>
          this.leavePolicyService.addLeavePolicyToCollectionIfMissing<ILeavePolicy>(leavePolicies, this.customLeavePolicy?.leavePolicy)
        )
      )
      .subscribe((leavePolicies: ILeavePolicy[]) => (this.leavePoliciesSharedCollection = leavePolicies));

    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.customLeavePolicy?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
