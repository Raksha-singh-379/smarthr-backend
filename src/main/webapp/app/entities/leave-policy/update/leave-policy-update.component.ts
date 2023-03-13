import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LeavePolicyFormService, LeavePolicyFormGroup } from './leave-policy-form.service';
import { ILeavePolicy } from '../leave-policy.model';
import { LeavePolicyService } from '../service/leave-policy.service';
import { ILeaveType } from 'app/entities/leave-type/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type/service/leave-type.service';
import { IEmploymentType } from 'app/entities/employment-type/employment-type.model';
import { EmploymentTypeService } from 'app/entities/employment-type/service/employment-type.service';

@Component({
  selector: 'jhi-leave-policy-update',
  templateUrl: './leave-policy-update.component.html',
})
export class LeavePolicyUpdateComponent implements OnInit {
  isSaving = false;
  leavePolicy: ILeavePolicy | null = null;

  leaveTypesSharedCollection: ILeaveType[] = [];
  employmentTypesSharedCollection: IEmploymentType[] = [];

  editForm: LeavePolicyFormGroup = this.leavePolicyFormService.createLeavePolicyFormGroup();

  constructor(
    protected leavePolicyService: LeavePolicyService,
    protected leavePolicyFormService: LeavePolicyFormService,
    protected leaveTypeService: LeaveTypeService,
    protected employmentTypeService: EmploymentTypeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLeaveType = (o1: ILeaveType | null, o2: ILeaveType | null): boolean => this.leaveTypeService.compareLeaveType(o1, o2);

  compareEmploymentType = (o1: IEmploymentType | null, o2: IEmploymentType | null): boolean =>
    this.employmentTypeService.compareEmploymentType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leavePolicy }) => {
      this.leavePolicy = leavePolicy;
      if (leavePolicy) {
        this.updateForm(leavePolicy);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leavePolicy = this.leavePolicyFormService.getLeavePolicy(this.editForm);
    if (leavePolicy.id !== null) {
      this.subscribeToSaveResponse(this.leavePolicyService.update(leavePolicy));
    } else {
      this.subscribeToSaveResponse(this.leavePolicyService.create(leavePolicy));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeavePolicy>>): void {
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

  protected updateForm(leavePolicy: ILeavePolicy): void {
    this.leavePolicy = leavePolicy;
    this.leavePolicyFormService.resetForm(this.editForm, leavePolicy);

    this.leaveTypesSharedCollection = this.leaveTypeService.addLeaveTypeToCollectionIfMissing<ILeaveType>(
      this.leaveTypesSharedCollection,
      leavePolicy.leaveType
    );
    this.employmentTypesSharedCollection = this.employmentTypeService.addEmploymentTypeToCollectionIfMissing<IEmploymentType>(
      this.employmentTypesSharedCollection,
      leavePolicy.employmentType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.leaveTypeService
      .query()
      .pipe(map((res: HttpResponse<ILeaveType[]>) => res.body ?? []))
      .pipe(
        map((leaveTypes: ILeaveType[]) =>
          this.leaveTypeService.addLeaveTypeToCollectionIfMissing<ILeaveType>(leaveTypes, this.leavePolicy?.leaveType)
        )
      )
      .subscribe((leaveTypes: ILeaveType[]) => (this.leaveTypesSharedCollection = leaveTypes));

    this.employmentTypeService
      .query()
      .pipe(map((res: HttpResponse<IEmploymentType[]>) => res.body ?? []))
      .pipe(
        map((employmentTypes: IEmploymentType[]) =>
          this.employmentTypeService.addEmploymentTypeToCollectionIfMissing<IEmploymentType>(
            employmentTypes,
            this.leavePolicy?.employmentType
          )
        )
      )
      .subscribe((employmentTypes: IEmploymentType[]) => (this.employmentTypesSharedCollection = employmentTypes));
  }
}
