import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReportingStructure, NewReportingStructure } from '../reporting-structure.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReportingStructure for edit and NewReportingStructureFormGroupInput for create.
 */
type ReportingStructureFormGroupInput = IReportingStructure | PartialWithRequiredKeyOf<NewReportingStructure>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IReportingStructure | NewReportingStructure> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type ReportingStructureFormRawValue = FormValueOf<IReportingStructure>;

type NewReportingStructureFormRawValue = FormValueOf<NewReportingStructure>;

type ReportingStructureFormDefaults = Pick<NewReportingStructure, 'id' | 'lastModified'>;

type ReportingStructureFormGroupContent = {
  id: FormControl<ReportingStructureFormRawValue['id'] | NewReportingStructure['id']>;
  employeeId: FormControl<ReportingStructureFormRawValue['employeeId']>;
  reportingEmpId: FormControl<ReportingStructureFormRawValue['reportingEmpId']>;
  status: FormControl<ReportingStructureFormRawValue['status']>;
  reportingStrId: FormControl<ReportingStructureFormRawValue['reportingStrId']>;
  lastModified: FormControl<ReportingStructureFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ReportingStructureFormRawValue['lastModifiedBy']>;
  companyId: FormControl<ReportingStructureFormRawValue['companyId']>;
};

export type ReportingStructureFormGroup = FormGroup<ReportingStructureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReportingStructureFormService {
  createReportingStructureFormGroup(reportingStructure: ReportingStructureFormGroupInput = { id: null }): ReportingStructureFormGroup {
    const reportingStructureRawValue = this.convertReportingStructureToReportingStructureRawValue({
      ...this.getFormDefaults(),
      ...reportingStructure,
    });
    return new FormGroup<ReportingStructureFormGroupContent>({
      id: new FormControl(
        { value: reportingStructureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      employeeId: new FormControl(reportingStructureRawValue.employeeId),
      reportingEmpId: new FormControl(reportingStructureRawValue.reportingEmpId),
      status: new FormControl(reportingStructureRawValue.status),
      reportingStrId: new FormControl(reportingStructureRawValue.reportingStrId),
      lastModified: new FormControl(reportingStructureRawValue.lastModified),
      lastModifiedBy: new FormControl(reportingStructureRawValue.lastModifiedBy),
      companyId: new FormControl(reportingStructureRawValue.companyId),
    });
  }

  getReportingStructure(form: ReportingStructureFormGroup): IReportingStructure | NewReportingStructure {
    return this.convertReportingStructureRawValueToReportingStructure(
      form.getRawValue() as ReportingStructureFormRawValue | NewReportingStructureFormRawValue
    );
  }

  resetForm(form: ReportingStructureFormGroup, reportingStructure: ReportingStructureFormGroupInput): void {
    const reportingStructureRawValue = this.convertReportingStructureToReportingStructureRawValue({
      ...this.getFormDefaults(),
      ...reportingStructure,
    });
    form.reset(
      {
        ...reportingStructureRawValue,
        id: { value: reportingStructureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReportingStructureFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
    };
  }

  private convertReportingStructureRawValueToReportingStructure(
    rawReportingStructure: ReportingStructureFormRawValue | NewReportingStructureFormRawValue
  ): IReportingStructure | NewReportingStructure {
    return {
      ...rawReportingStructure,
      lastModified: dayjs(rawReportingStructure.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertReportingStructureToReportingStructureRawValue(
    reportingStructure: IReportingStructure | (Partial<NewReportingStructure> & ReportingStructureFormDefaults)
  ): ReportingStructureFormRawValue | PartialWithRequiredKeyOf<NewReportingStructureFormRawValue> {
    return {
      ...reportingStructure,
      lastModified: reportingStructure.lastModified ? reportingStructure.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
