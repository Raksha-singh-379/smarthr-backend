import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITimeSheet, NewTimeSheet } from '../time-sheet.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITimeSheet for edit and NewTimeSheetFormGroupInput for create.
 */
type TimeSheetFormGroupInput = ITimeSheet | PartialWithRequiredKeyOf<NewTimeSheet>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITimeSheet | NewTimeSheet> = Omit<T, 'checkIn' | 'checkOut' | 'date' | 'lastModified'> & {
  checkIn?: string | null;
  checkOut?: string | null;
  date?: string | null;
  lastModified?: string | null;
};

type TimeSheetFormRawValue = FormValueOf<ITimeSheet>;

type NewTimeSheetFormRawValue = FormValueOf<NewTimeSheet>;

type TimeSheetFormDefaults = Pick<NewTimeSheet, 'id' | 'checkIn' | 'checkOut' | 'date' | 'hasCheckedIn' | 'lastModified'>;

type TimeSheetFormGroupContent = {
  id: FormControl<TimeSheetFormRawValue['id'] | NewTimeSheet['id']>;
  checkIn: FormControl<TimeSheetFormRawValue['checkIn']>;
  checkOut: FormControl<TimeSheetFormRawValue['checkOut']>;
  date: FormControl<TimeSheetFormRawValue['date']>;
  hasCheckedIn: FormControl<TimeSheetFormRawValue['hasCheckedIn']>;
  companyId: FormControl<TimeSheetFormRawValue['companyId']>;
  status: FormControl<TimeSheetFormRawValue['status']>;
  lastModified: FormControl<TimeSheetFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<TimeSheetFormRawValue['lastModifiedBy']>;
  attendance: FormControl<TimeSheetFormRawValue['attendance']>;
};

export type TimeSheetFormGroup = FormGroup<TimeSheetFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TimeSheetFormService {
  createTimeSheetFormGroup(timeSheet: TimeSheetFormGroupInput = { id: null }): TimeSheetFormGroup {
    const timeSheetRawValue = this.convertTimeSheetToTimeSheetRawValue({
      ...this.getFormDefaults(),
      ...timeSheet,
    });
    return new FormGroup<TimeSheetFormGroupContent>({
      id: new FormControl(
        { value: timeSheetRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      checkIn: new FormControl(timeSheetRawValue.checkIn),
      checkOut: new FormControl(timeSheetRawValue.checkOut),
      date: new FormControl(timeSheetRawValue.date),
      hasCheckedIn: new FormControl(timeSheetRawValue.hasCheckedIn),
      companyId: new FormControl(timeSheetRawValue.companyId),
      status: new FormControl(timeSheetRawValue.status),
      lastModified: new FormControl(timeSheetRawValue.lastModified),
      lastModifiedBy: new FormControl(timeSheetRawValue.lastModifiedBy),
      attendance: new FormControl(timeSheetRawValue.attendance),
    });
  }

  getTimeSheet(form: TimeSheetFormGroup): ITimeSheet | NewTimeSheet {
    return this.convertTimeSheetRawValueToTimeSheet(form.getRawValue() as TimeSheetFormRawValue | NewTimeSheetFormRawValue);
  }

  resetForm(form: TimeSheetFormGroup, timeSheet: TimeSheetFormGroupInput): void {
    const timeSheetRawValue = this.convertTimeSheetToTimeSheetRawValue({ ...this.getFormDefaults(), ...timeSheet });
    form.reset(
      {
        ...timeSheetRawValue,
        id: { value: timeSheetRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TimeSheetFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      checkIn: currentTime,
      checkOut: currentTime,
      date: currentTime,
      hasCheckedIn: false,
      lastModified: currentTime,
    };
  }

  private convertTimeSheetRawValueToTimeSheet(rawTimeSheet: TimeSheetFormRawValue | NewTimeSheetFormRawValue): ITimeSheet | NewTimeSheet {
    return {
      ...rawTimeSheet,
      checkIn: dayjs(rawTimeSheet.checkIn, DATE_TIME_FORMAT),
      checkOut: dayjs(rawTimeSheet.checkOut, DATE_TIME_FORMAT),
      date: dayjs(rawTimeSheet.date, DATE_TIME_FORMAT),
      lastModified: dayjs(rawTimeSheet.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertTimeSheetToTimeSheetRawValue(
    timeSheet: ITimeSheet | (Partial<NewTimeSheet> & TimeSheetFormDefaults)
  ): TimeSheetFormRawValue | PartialWithRequiredKeyOf<NewTimeSheetFormRawValue> {
    return {
      ...timeSheet,
      checkIn: timeSheet.checkIn ? timeSheet.checkIn.format(DATE_TIME_FORMAT) : undefined,
      checkOut: timeSheet.checkOut ? timeSheet.checkOut.format(DATE_TIME_FORMAT) : undefined,
      date: timeSheet.date ? timeSheet.date.format(DATE_TIME_FORMAT) : undefined,
      lastModified: timeSheet.lastModified ? timeSheet.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
