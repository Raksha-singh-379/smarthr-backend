import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPersonalDetails, NewPersonalDetails } from '../personal-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonalDetails for edit and NewPersonalDetailsFormGroupInput for create.
 */
type PersonalDetailsFormGroupInput = IPersonalDetails | PartialWithRequiredKeyOf<NewPersonalDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPersonalDetails | NewPersonalDetails> = Omit<T, 'passportExpDate' | 'lastModified'> & {
  passportExpDate?: string | null;
  lastModified?: string | null;
};

type PersonalDetailsFormRawValue = FormValueOf<IPersonalDetails>;

type NewPersonalDetailsFormRawValue = FormValueOf<NewPersonalDetails>;

type PersonalDetailsFormDefaults = Pick<NewPersonalDetails, 'id' | 'passportExpDate' | 'isSpousEmployed' | 'lastModified'>;

type PersonalDetailsFormGroupContent = {
  id: FormControl<PersonalDetailsFormRawValue['id'] | NewPersonalDetails['id']>;
  passportNo: FormControl<PersonalDetailsFormRawValue['passportNo']>;
  passportExpDate: FormControl<PersonalDetailsFormRawValue['passportExpDate']>;
  telephoneNo: FormControl<PersonalDetailsFormRawValue['telephoneNo']>;
  nationality: FormControl<PersonalDetailsFormRawValue['nationality']>;
  maritalStatus: FormControl<PersonalDetailsFormRawValue['maritalStatus']>;
  religion: FormControl<PersonalDetailsFormRawValue['religion']>;
  isSpousEmployed: FormControl<PersonalDetailsFormRawValue['isSpousEmployed']>;
  lastModified: FormControl<PersonalDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<PersonalDetailsFormRawValue['lastModifiedBy']>;
  status: FormControl<PersonalDetailsFormRawValue['status']>;
  employeeId: FormControl<PersonalDetailsFormRawValue['employeeId']>;
  companyId: FormControl<PersonalDetailsFormRawValue['companyId']>;
  personalIdNo: FormControl<PersonalDetailsFormRawValue['personalIdNo']>;
  bloodGroup: FormControl<PersonalDetailsFormRawValue['bloodGroup']>;
  dateOfBirth: FormControl<PersonalDetailsFormRawValue['dateOfBirth']>;
};

export type PersonalDetailsFormGroup = FormGroup<PersonalDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonalDetailsFormService {
  createPersonalDetailsFormGroup(personalDetails: PersonalDetailsFormGroupInput = { id: null }): PersonalDetailsFormGroup {
    const personalDetailsRawValue = this.convertPersonalDetailsToPersonalDetailsRawValue({
      ...this.getFormDefaults(),
      ...personalDetails,
    });
    return new FormGroup<PersonalDetailsFormGroupContent>({
      id: new FormControl(
        { value: personalDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      passportNo: new FormControl(personalDetailsRawValue.passportNo),
      passportExpDate: new FormControl(personalDetailsRawValue.passportExpDate),
      telephoneNo: new FormControl(personalDetailsRawValue.telephoneNo),
      nationality: new FormControl(personalDetailsRawValue.nationality),
      maritalStatus: new FormControl(personalDetailsRawValue.maritalStatus),
      religion: new FormControl(personalDetailsRawValue.religion),
      isSpousEmployed: new FormControl(personalDetailsRawValue.isSpousEmployed),
      lastModified: new FormControl(personalDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(personalDetailsRawValue.lastModifiedBy),
      status: new FormControl(personalDetailsRawValue.status),
      employeeId: new FormControl(personalDetailsRawValue.employeeId),
      companyId: new FormControl(personalDetailsRawValue.companyId),
      personalIdNo: new FormControl(personalDetailsRawValue.personalIdNo),
      bloodGroup: new FormControl(personalDetailsRawValue.bloodGroup),
      dateOfBirth: new FormControl(personalDetailsRawValue.dateOfBirth),
    });
  }

  getPersonalDetails(form: PersonalDetailsFormGroup): IPersonalDetails | NewPersonalDetails {
    return this.convertPersonalDetailsRawValueToPersonalDetails(
      form.getRawValue() as PersonalDetailsFormRawValue | NewPersonalDetailsFormRawValue
    );
  }

  resetForm(form: PersonalDetailsFormGroup, personalDetails: PersonalDetailsFormGroupInput): void {
    const personalDetailsRawValue = this.convertPersonalDetailsToPersonalDetailsRawValue({ ...this.getFormDefaults(), ...personalDetails });
    form.reset(
      {
        ...personalDetailsRawValue,
        id: { value: personalDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PersonalDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      passportExpDate: currentTime,
      isSpousEmployed: false,
      lastModified: currentTime,
    };
  }

  private convertPersonalDetailsRawValueToPersonalDetails(
    rawPersonalDetails: PersonalDetailsFormRawValue | NewPersonalDetailsFormRawValue
  ): IPersonalDetails | NewPersonalDetails {
    return {
      ...rawPersonalDetails,
      passportExpDate: dayjs(rawPersonalDetails.passportExpDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawPersonalDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertPersonalDetailsToPersonalDetailsRawValue(
    personalDetails: IPersonalDetails | (Partial<NewPersonalDetails> & PersonalDetailsFormDefaults)
  ): PersonalDetailsFormRawValue | PartialWithRequiredKeyOf<NewPersonalDetailsFormRawValue> {
    return {
      ...personalDetails,
      passportExpDate: personalDetails.passportExpDate ? personalDetails.passportExpDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: personalDetails.lastModified ? personalDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
