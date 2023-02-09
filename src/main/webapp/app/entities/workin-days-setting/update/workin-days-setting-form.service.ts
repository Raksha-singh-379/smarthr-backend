import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IWorkinDaysSetting, NewWorkinDaysSetting } from '../workin-days-setting.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkinDaysSetting for edit and NewWorkinDaysSettingFormGroupInput for create.
 */
type WorkinDaysSettingFormGroupInput = IWorkinDaysSetting | PartialWithRequiredKeyOf<NewWorkinDaysSetting>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IWorkinDaysSetting | NewWorkinDaysSetting> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type WorkinDaysSettingFormRawValue = FormValueOf<IWorkinDaysSetting>;

type NewWorkinDaysSettingFormRawValue = FormValueOf<NewWorkinDaysSetting>;

type WorkinDaysSettingFormDefaults = Pick<NewWorkinDaysSetting, 'id' | 'lastModified'>;

type WorkinDaysSettingFormGroupContent = {
  id: FormControl<WorkinDaysSettingFormRawValue['id'] | NewWorkinDaysSetting['id']>;
  lastModified: FormControl<WorkinDaysSettingFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<WorkinDaysSettingFormRawValue['lastModifiedBy']>;
  status: FormControl<WorkinDaysSettingFormRawValue['status']>;
  companyId: FormControl<WorkinDaysSettingFormRawValue['companyId']>;
};

export type WorkinDaysSettingFormGroup = FormGroup<WorkinDaysSettingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkinDaysSettingFormService {
  createWorkinDaysSettingFormGroup(workinDaysSetting: WorkinDaysSettingFormGroupInput = { id: null }): WorkinDaysSettingFormGroup {
    const workinDaysSettingRawValue = this.convertWorkinDaysSettingToWorkinDaysSettingRawValue({
      ...this.getFormDefaults(),
      ...workinDaysSetting,
    });
    return new FormGroup<WorkinDaysSettingFormGroupContent>({
      id: new FormControl(
        { value: workinDaysSettingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      lastModified: new FormControl(workinDaysSettingRawValue.lastModified),
      lastModifiedBy: new FormControl(workinDaysSettingRawValue.lastModifiedBy),
      status: new FormControl(workinDaysSettingRawValue.status),
      companyId: new FormControl(workinDaysSettingRawValue.companyId),
    });
  }

  getWorkinDaysSetting(form: WorkinDaysSettingFormGroup): IWorkinDaysSetting | NewWorkinDaysSetting {
    return this.convertWorkinDaysSettingRawValueToWorkinDaysSetting(
      form.getRawValue() as WorkinDaysSettingFormRawValue | NewWorkinDaysSettingFormRawValue
    );
  }

  resetForm(form: WorkinDaysSettingFormGroup, workinDaysSetting: WorkinDaysSettingFormGroupInput): void {
    const workinDaysSettingRawValue = this.convertWorkinDaysSettingToWorkinDaysSettingRawValue({
      ...this.getFormDefaults(),
      ...workinDaysSetting,
    });
    form.reset(
      {
        ...workinDaysSettingRawValue,
        id: { value: workinDaysSettingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): WorkinDaysSettingFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
    };
  }

  private convertWorkinDaysSettingRawValueToWorkinDaysSetting(
    rawWorkinDaysSetting: WorkinDaysSettingFormRawValue | NewWorkinDaysSettingFormRawValue
  ): IWorkinDaysSetting | NewWorkinDaysSetting {
    return {
      ...rawWorkinDaysSetting,
      lastModified: dayjs(rawWorkinDaysSetting.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertWorkinDaysSettingToWorkinDaysSettingRawValue(
    workinDaysSetting: IWorkinDaysSetting | (Partial<NewWorkinDaysSetting> & WorkinDaysSettingFormDefaults)
  ): WorkinDaysSettingFormRawValue | PartialWithRequiredKeyOf<NewWorkinDaysSettingFormRawValue> {
    return {
      ...workinDaysSetting,
      lastModified: workinDaysSetting.lastModified ? workinDaysSetting.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
