import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAttendance, NewAttendance } from '../attendance.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAttendance for edit and NewAttendanceFormGroupInput for create.
 */
type AttendanceFormGroupInput = IAttendance | PartialWithRequiredKeyOf<NewAttendance>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAttendance | NewAttendance> = Omit<T, 'date' | 'lastModified'> & {
  date?: string | null;
  lastModified?: string | null;
};

type AttendanceFormRawValue = FormValueOf<IAttendance>;

type NewAttendanceFormRawValue = FormValueOf<NewAttendance>;

type AttendanceFormDefaults = Pick<NewAttendance, 'id' | 'date' | 'lastModified'>;

type AttendanceFormGroupContent = {
  id: FormControl<AttendanceFormRawValue['id'] | NewAttendance['id']>;
  deviceInfo: FormControl<AttendanceFormRawValue['deviceInfo']>;
  latitude: FormControl<AttendanceFormRawValue['latitude']>;
  longitude: FormControl<AttendanceFormRawValue['longitude']>;
  date: FormControl<AttendanceFormRawValue['date']>;
  day: FormControl<AttendanceFormRawValue['day']>;
  hours: FormControl<AttendanceFormRawValue['hours']>;
  employeeId: FormControl<AttendanceFormRawValue['employeeId']>;
  companyId: FormControl<AttendanceFormRawValue['companyId']>;
  status: FormControl<AttendanceFormRawValue['status']>;
  lastModified: FormControl<AttendanceFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<AttendanceFormRawValue['lastModifiedBy']>;
};

export type AttendanceFormGroup = FormGroup<AttendanceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AttendanceFormService {
  createAttendanceFormGroup(attendance: AttendanceFormGroupInput = { id: null }): AttendanceFormGroup {
    const attendanceRawValue = this.convertAttendanceToAttendanceRawValue({
      ...this.getFormDefaults(),
      ...attendance,
    });
    return new FormGroup<AttendanceFormGroupContent>({
      id: new FormControl(
        { value: attendanceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      deviceInfo: new FormControl(attendanceRawValue.deviceInfo),
      latitude: new FormControl(attendanceRawValue.latitude),
      longitude: new FormControl(attendanceRawValue.longitude),
      date: new FormControl(attendanceRawValue.date),
      day: new FormControl(attendanceRawValue.day),
      hours: new FormControl(attendanceRawValue.hours),
      employeeId: new FormControl(attendanceRawValue.employeeId),
      companyId: new FormControl(attendanceRawValue.companyId),
      status: new FormControl(attendanceRawValue.status),
      lastModified: new FormControl(attendanceRawValue.lastModified),
      lastModifiedBy: new FormControl(attendanceRawValue.lastModifiedBy),
    });
  }

  getAttendance(form: AttendanceFormGroup): IAttendance | NewAttendance {
    return this.convertAttendanceRawValueToAttendance(form.getRawValue() as AttendanceFormRawValue | NewAttendanceFormRawValue);
  }

  resetForm(form: AttendanceFormGroup, attendance: AttendanceFormGroupInput): void {
    const attendanceRawValue = this.convertAttendanceToAttendanceRawValue({ ...this.getFormDefaults(), ...attendance });
    form.reset(
      {
        ...attendanceRawValue,
        id: { value: attendanceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AttendanceFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
      lastModified: currentTime,
    };
  }

  private convertAttendanceRawValueToAttendance(
    rawAttendance: AttendanceFormRawValue | NewAttendanceFormRawValue
  ): IAttendance | NewAttendance {
    return {
      ...rawAttendance,
      date: dayjs(rawAttendance.date, DATE_TIME_FORMAT),
      lastModified: dayjs(rawAttendance.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertAttendanceToAttendanceRawValue(
    attendance: IAttendance | (Partial<NewAttendance> & AttendanceFormDefaults)
  ): AttendanceFormRawValue | PartialWithRequiredKeyOf<NewAttendanceFormRawValue> {
    return {
      ...attendance,
      date: attendance.date ? attendance.date.format(DATE_TIME_FORMAT) : undefined,
      lastModified: attendance.lastModified ? attendance.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
