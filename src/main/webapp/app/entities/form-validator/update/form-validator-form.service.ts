import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFormValidator, NewFormValidator } from '../form-validator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFormValidator for edit and NewFormValidatorFormGroupInput for create.
 */
type FormValidatorFormGroupInput = IFormValidator | PartialWithRequiredKeyOf<NewFormValidator>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFormValidator | NewFormValidator> = Omit<T, 'createdOn'> & {
  createdOn?: string | null;
};

type FormValidatorFormRawValue = FormValueOf<IFormValidator>;

type NewFormValidatorFormRawValue = FormValueOf<NewFormValidator>;

type FormValidatorFormDefaults = Pick<NewFormValidator, 'id' | 'createdOn'>;

type FormValidatorFormGroupContent = {
  id: FormControl<FormValidatorFormRawValue['id'] | NewFormValidator['id']>;
  type: FormControl<FormValidatorFormRawValue['type']>;
  value: FormControl<FormValidatorFormRawValue['value']>;
  formName: FormControl<FormValidatorFormRawValue['formName']>;
  fieldName: FormControl<FormValidatorFormRawValue['fieldName']>;
  companyId: FormControl<FormValidatorFormRawValue['companyId']>;
  createdBy: FormControl<FormValidatorFormRawValue['createdBy']>;
  createdOn: FormControl<FormValidatorFormRawValue['createdOn']>;
};

export type FormValidatorFormGroup = FormGroup<FormValidatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FormValidatorFormService {
  createFormValidatorFormGroup(formValidator: FormValidatorFormGroupInput = { id: null }): FormValidatorFormGroup {
    const formValidatorRawValue = this.convertFormValidatorToFormValidatorRawValue({
      ...this.getFormDefaults(),
      ...formValidator,
    });
    return new FormGroup<FormValidatorFormGroupContent>({
      id: new FormControl(
        { value: formValidatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(formValidatorRawValue.type),
      value: new FormControl(formValidatorRawValue.value),
      formName: new FormControl(formValidatorRawValue.formName),
      fieldName: new FormControl(formValidatorRawValue.fieldName),
      companyId: new FormControl(formValidatorRawValue.companyId),
      createdBy: new FormControl(formValidatorRawValue.createdBy),
      createdOn: new FormControl(formValidatorRawValue.createdOn),
    });
  }

  getFormValidator(form: FormValidatorFormGroup): IFormValidator | NewFormValidator {
    return this.convertFormValidatorRawValueToFormValidator(form.getRawValue() as FormValidatorFormRawValue | NewFormValidatorFormRawValue);
  }

  resetForm(form: FormValidatorFormGroup, formValidator: FormValidatorFormGroupInput): void {
    const formValidatorRawValue = this.convertFormValidatorToFormValidatorRawValue({ ...this.getFormDefaults(), ...formValidator });
    form.reset(
      {
        ...formValidatorRawValue,
        id: { value: formValidatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FormValidatorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdOn: currentTime,
    };
  }

  private convertFormValidatorRawValueToFormValidator(
    rawFormValidator: FormValidatorFormRawValue | NewFormValidatorFormRawValue
  ): IFormValidator | NewFormValidator {
    return {
      ...rawFormValidator,
      createdOn: dayjs(rawFormValidator.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertFormValidatorToFormValidatorRawValue(
    formValidator: IFormValidator | (Partial<NewFormValidator> & FormValidatorFormDefaults)
  ): FormValidatorFormRawValue | PartialWithRequiredKeyOf<NewFormValidatorFormRawValue> {
    return {
      ...formValidator,
      createdOn: formValidator.createdOn ? formValidator.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
