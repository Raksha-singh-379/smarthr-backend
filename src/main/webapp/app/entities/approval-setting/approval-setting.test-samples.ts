import dayjs from 'dayjs/esm';

import { IApprovalSetting, NewApprovalSetting } from './approval-setting.model';

export const sampleWithRequiredData: IApprovalSetting = {
  id: 49229,
};

export const sampleWithPartialData: IApprovalSetting = {
  id: 93262,
  status: 'Checking Japan',
  companyId: 13869,
};

export const sampleWithFullData: IApprovalSetting = {
  id: 79824,
  isSequenceApproval: true,
  isSimultaneousApproval: true,
  lastModified: dayjs('2023-02-08T17:47'),
  lastModifiedBy: 'Unbranded Kentucky 60',
  status: 'payment FTP',
  companyId: 21656,
};

export const sampleWithNewData: NewApprovalSetting = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
