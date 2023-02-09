import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReEnumerationFormService, ReEnumerationFormGroup } from './re-enumeration-form.service';
import { IReEnumeration } from '../re-enumeration.model';
import { ReEnumerationService } from '../service/re-enumeration.service';

@Component({
  selector: 'jhi-re-enumeration-update',
  templateUrl: './re-enumeration-update.component.html',
})
export class ReEnumerationUpdateComponent implements OnInit {
  isSaving = false;
  reEnumeration: IReEnumeration | null = null;

  editForm: ReEnumerationFormGroup = this.reEnumerationFormService.createReEnumerationFormGroup();

  constructor(
    protected reEnumerationService: ReEnumerationService,
    protected reEnumerationFormService: ReEnumerationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reEnumeration }) => {
      this.reEnumeration = reEnumeration;
      if (reEnumeration) {
        this.updateForm(reEnumeration);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reEnumeration = this.reEnumerationFormService.getReEnumeration(this.editForm);
    if (reEnumeration.id !== null) {
      this.subscribeToSaveResponse(this.reEnumerationService.update(reEnumeration));
    } else {
      this.subscribeToSaveResponse(this.reEnumerationService.create(reEnumeration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReEnumeration>>): void {
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

  protected updateForm(reEnumeration: IReEnumeration): void {
    this.reEnumeration = reEnumeration;
    this.reEnumerationFormService.resetForm(this.editForm, reEnumeration);
  }
}
