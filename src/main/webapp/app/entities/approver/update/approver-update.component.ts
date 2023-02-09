import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ApproverFormService, ApproverFormGroup } from './approver-form.service';
import { IApprover } from '../approver.model';
import { ApproverService } from '../service/approver.service';

@Component({
  selector: 'jhi-approver-update',
  templateUrl: './approver-update.component.html',
})
export class ApproverUpdateComponent implements OnInit {
  isSaving = false;
  approver: IApprover | null = null;

  editForm: ApproverFormGroup = this.approverFormService.createApproverFormGroup();

  constructor(
    protected approverService: ApproverService,
    protected approverFormService: ApproverFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ approver }) => {
      this.approver = approver;
      if (approver) {
        this.updateForm(approver);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const approver = this.approverFormService.getApprover(this.editForm);
    if (approver.id !== null) {
      this.subscribeToSaveResponse(this.approverService.update(approver));
    } else {
      this.subscribeToSaveResponse(this.approverService.create(approver));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApprover>>): void {
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

  protected updateForm(approver: IApprover): void {
    this.approver = approver;
    this.approverFormService.resetForm(this.editForm, approver);
  }
}
