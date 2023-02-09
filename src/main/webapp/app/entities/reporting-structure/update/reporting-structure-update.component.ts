import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReportingStructureFormService, ReportingStructureFormGroup } from './reporting-structure-form.service';
import { IReportingStructure } from '../reporting-structure.model';
import { ReportingStructureService } from '../service/reporting-structure.service';

@Component({
  selector: 'jhi-reporting-structure-update',
  templateUrl: './reporting-structure-update.component.html',
})
export class ReportingStructureUpdateComponent implements OnInit {
  isSaving = false;
  reportingStructure: IReportingStructure | null = null;

  editForm: ReportingStructureFormGroup = this.reportingStructureFormService.createReportingStructureFormGroup();

  constructor(
    protected reportingStructureService: ReportingStructureService,
    protected reportingStructureFormService: ReportingStructureFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportingStructure }) => {
      this.reportingStructure = reportingStructure;
      if (reportingStructure) {
        this.updateForm(reportingStructure);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reportingStructure = this.reportingStructureFormService.getReportingStructure(this.editForm);
    if (reportingStructure.id !== null) {
      this.subscribeToSaveResponse(this.reportingStructureService.update(reportingStructure));
    } else {
      this.subscribeToSaveResponse(this.reportingStructureService.create(reportingStructure));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportingStructure>>): void {
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

  protected updateForm(reportingStructure: IReportingStructure): void {
    this.reportingStructure = reportingStructure;
    this.reportingStructureFormService.resetForm(this.editForm, reportingStructure);
  }
}
