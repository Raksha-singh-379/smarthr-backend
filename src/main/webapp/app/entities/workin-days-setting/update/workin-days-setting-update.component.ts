import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { WorkinDaysSettingFormService, WorkinDaysSettingFormGroup } from './workin-days-setting-form.service';
import { IWorkinDaysSetting } from '../workin-days-setting.model';
import { WorkinDaysSettingService } from '../service/workin-days-setting.service';

@Component({
  selector: 'jhi-workin-days-setting-update',
  templateUrl: './workin-days-setting-update.component.html',
})
export class WorkinDaysSettingUpdateComponent implements OnInit {
  isSaving = false;
  workinDaysSetting: IWorkinDaysSetting | null = null;

  editForm: WorkinDaysSettingFormGroup = this.workinDaysSettingFormService.createWorkinDaysSettingFormGroup();

  constructor(
    protected workinDaysSettingService: WorkinDaysSettingService,
    protected workinDaysSettingFormService: WorkinDaysSettingFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workinDaysSetting }) => {
      this.workinDaysSetting = workinDaysSetting;
      if (workinDaysSetting) {
        this.updateForm(workinDaysSetting);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workinDaysSetting = this.workinDaysSettingFormService.getWorkinDaysSetting(this.editForm);
    if (workinDaysSetting.id !== null) {
      this.subscribeToSaveResponse(this.workinDaysSettingService.update(workinDaysSetting));
    } else {
      this.subscribeToSaveResponse(this.workinDaysSettingService.create(workinDaysSetting));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkinDaysSetting>>): void {
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

  protected updateForm(workinDaysSetting: IWorkinDaysSetting): void {
    this.workinDaysSetting = workinDaysSetting;
    this.workinDaysSettingFormService.resetForm(this.editForm, workinDaysSetting);
  }
}
