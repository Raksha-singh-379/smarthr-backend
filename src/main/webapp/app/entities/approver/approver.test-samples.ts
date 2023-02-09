import dayjs from 'dayjs/esm';

import { IApprover, NewApprover } from './approver.model';

export const sampleWithRequiredData: IApprover = {
  id: 82694,
};

export const sampleWithPartialData: IApprover = {
  id: 26398,
  approverName: 'Montana Rican Cambridgeshire',
  lastModifiedBy: 'primary cyan Avon',
  approvalSettingId: 98785,
  departmentId: 17007,
};

export const sampleWithFullData: IApprover = {
  id: 74198,
  approverName: 'Peso',
  lastModified: dayjs('2023-02-08T10:16'),
  lastModifiedBy: 'Dakota Savings',
  status: 'tan Awesome Markets',
  approvalSettingId: 57378,
  departmentId: 53954,
  companyId: 43132,
};

export const sampleWithNewData: NewApprover = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
