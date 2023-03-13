import dayjs from 'dayjs/esm';

import { ICustomLeavePolicy, NewCustomLeavePolicy } from './custom-leave-policy.model';

export const sampleWithRequiredData: ICustomLeavePolicy = {
  id: 23554,
};

export const sampleWithPartialData: ICustomLeavePolicy = {
  id: 2885,
  leavePolicyId: 36289,
  companyId: 54104,
  lastModified: dayjs('2023-03-13T06:33'),
  lastModifiedBy: 'user-facing contextually-based Islands,',
};

export const sampleWithFullData: ICustomLeavePolicy = {
  id: 84361,
  customPolicyName: 'salmon programming',
  leavePolicyId: 13946,
  days: 38494,
  companyId: 55186,
  status: 'maximized Cheese optimize',
  lastModified: dayjs('2023-03-12T10:14'),
  lastModifiedBy: 'optimize',
};

export const sampleWithNewData: NewCustomLeavePolicy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
