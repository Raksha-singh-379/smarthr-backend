import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IApprover, NewApprover } from '../approver.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IApprover for edit and NewApproverFormGroupInput for create.
 */
type ApproverFormGroupInput = IApprover | PartialWithRequiredKeyOf<NewApprover>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IApprover | NewApprover> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type ApproverFormRawValue = FormValueOf<IApprover>;

type NewApproverFormRawValue = FormValueOf<NewApprover>;

type ApproverFormDefaults = Pick<NewApprover, 'id' | 'lastModified'>;

type ApproverFormGroupContent = {
  id: FormControl<ApproverFormRawValue['id'] | NewApprover['id']>;
  approverName: FormControl<ApproverFormRawValue['approverName']>;
  lastModified: FormControl<ApproverFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ApproverFormRawValue['lastModifiedBy']>;
  status: FormControl<ApproverFormRawValue['status']>;
  approvalSettingId: FormControl<ApproverFormRawValue['approvalSettingId']>;
  departmentId: FormControl<ApproverFormRawValue['departmentId']>;
  companyId: FormControl<ApproverFormRawValue['companyId']>;
};

export type ApproverFormGroup = FormGroup<ApproverFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ApproverFormService {
  createApproverFormGroup(approver: ApproverFormGroupInput = { id: null }): ApproverFormGroup {
    const approverRawValue = this.convertApproverToApproverRawValue({
      ...this.getFormDefaults(),
      ...approver,
    });
    return new FormGroup<ApproverFormGroupContent>({
      id: new FormControl(
        { value: approverRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      approverName: new FormControl(approverRawValue.approverName),
      lastModified: new FormControl(approverRawValue.lastModified),
      lastModifiedBy: new FormControl(approverRawValue.lastModifiedBy),
      status: new FormControl(approverRawValue.status),
      approvalSettingId: new FormControl(approverRawValue.approvalSettingId),
      departmentId: new FormControl(approverRawValue.departmentId),
      companyId: new FormControl(approverRawValue.companyId),
    });
  }

  getApprover(form: ApproverFormGroup): IApprover | NewApprover {
    return this.convertApproverRawValueToApprover(form.getRawValue() as ApproverFormRawValue | NewApproverFormRawValue);
  }

  resetForm(form: ApproverFormGroup, approver: ApproverFormGroupInput): void {
    const approverRawValue = this.convertApproverToApproverRawValue({ ...this.getFormDefaults(), ...approver });
    form.reset(
      {
        ...approverRawValue,
        id: { value: approverRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ApproverFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
    };
  }

  private convertApproverRawValueToApprover(rawApprover: ApproverFormRawValue | NewApproverFormRawValue): IApprover | NewApprover {
    return {
      ...rawApprover,
      lastModified: dayjs(rawApprover.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertApproverToApproverRawValue(
    approver: IApprover | (Partial<NewApprover> & ApproverFormDefaults)
  ): ApproverFormRawValue | PartialWithRequiredKeyOf<NewApproverFormRawValue> {
    return {
      ...approver,
      lastModified: approver.lastModified ? approver.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
