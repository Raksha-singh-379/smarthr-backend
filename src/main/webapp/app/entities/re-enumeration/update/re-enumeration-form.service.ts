import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IReEnumeration, NewReEnumeration } from '../re-enumeration.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IReEnumeration for edit and NewReEnumerationFormGroupInput for create.
 */
type ReEnumerationFormGroupInput = IReEnumeration | PartialWithRequiredKeyOf<NewReEnumeration>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IReEnumeration | NewReEnumeration> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type ReEnumerationFormRawValue = FormValueOf<IReEnumeration>;

type NewReEnumerationFormRawValue = FormValueOf<NewReEnumeration>;

type ReEnumerationFormDefaults = Pick<NewReEnumeration, 'id' | 'lastModified'>;

type ReEnumerationFormGroupContent = {
  id: FormControl<ReEnumerationFormRawValue['id'] | NewReEnumeration['id']>;
  salaryBasis: FormControl<ReEnumerationFormRawValue['salaryBasis']>;
  amount: FormControl<ReEnumerationFormRawValue['amount']>;
  paymentType: FormControl<ReEnumerationFormRawValue['paymentType']>;
  lastModified: FormControl<ReEnumerationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ReEnumerationFormRawValue['lastModifiedBy']>;
  status: FormControl<ReEnumerationFormRawValue['status']>;
  employeId: FormControl<ReEnumerationFormRawValue['employeId']>;
  companyId: FormControl<ReEnumerationFormRawValue['companyId']>;
};

export type ReEnumerationFormGroup = FormGroup<ReEnumerationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ReEnumerationFormService {
  createReEnumerationFormGroup(reEnumeration: ReEnumerationFormGroupInput = { id: null }): ReEnumerationFormGroup {
    const reEnumerationRawValue = this.convertReEnumerationToReEnumerationRawValue({
      ...this.getFormDefaults(),
      ...reEnumeration,
    });
    return new FormGroup<ReEnumerationFormGroupContent>({
      id: new FormControl(
        { value: reEnumerationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      salaryBasis: new FormControl(reEnumerationRawValue.salaryBasis),
      amount: new FormControl(reEnumerationRawValue.amount),
      paymentType: new FormControl(reEnumerationRawValue.paymentType),
      lastModified: new FormControl(reEnumerationRawValue.lastModified),
      lastModifiedBy: new FormControl(reEnumerationRawValue.lastModifiedBy),
      status: new FormControl(reEnumerationRawValue.status),
      employeId: new FormControl(reEnumerationRawValue.employeId),
      companyId: new FormControl(reEnumerationRawValue.companyId),
    });
  }

  getReEnumeration(form: ReEnumerationFormGroup): IReEnumeration | NewReEnumeration {
    return this.convertReEnumerationRawValueToReEnumeration(form.getRawValue() as ReEnumerationFormRawValue | NewReEnumerationFormRawValue);
  }

  resetForm(form: ReEnumerationFormGroup, reEnumeration: ReEnumerationFormGroupInput): void {
    const reEnumerationRawValue = this.convertReEnumerationToReEnumerationRawValue({ ...this.getFormDefaults(), ...reEnumeration });
    form.reset(
      {
        ...reEnumerationRawValue,
        id: { value: reEnumerationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ReEnumerationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
    };
  }

  private convertReEnumerationRawValueToReEnumeration(
    rawReEnumeration: ReEnumerationFormRawValue | NewReEnumerationFormRawValue
  ): IReEnumeration | NewReEnumeration {
    return {
      ...rawReEnumeration,
      lastModified: dayjs(rawReEnumeration.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertReEnumerationToReEnumerationRawValue(
    reEnumeration: IReEnumeration | (Partial<NewReEnumeration> & ReEnumerationFormDefaults)
  ): ReEnumerationFormRawValue | PartialWithRequiredKeyOf<NewReEnumerationFormRawValue> {
    return {
      ...reEnumeration,
      lastModified: reEnumeration.lastModified ? reEnumeration.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
