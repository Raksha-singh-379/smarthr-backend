import dayjs from 'dayjs/esm';

import { ILeavePolicy, NewLeavePolicy } from './leave-policy.model';

export const sampleWithRequiredData: ILeavePolicy = {
  id: 78331,
};

export const sampleWithPartialData: ILeavePolicy = {
  id: 1109,
  description: 'Berkshire',
  refTable: 'Corporate',
  refTableId: 10701,
  status: 'Sports Florida',
  lastModified: dayjs('2023-02-08T17:38'),
};

export const sampleWithFullData: ILeavePolicy = {
  id: 25294,
  isCarryForword: false,
  genderLeave: 90670,
  totalLeave: 87902,
  maxLeave: 34074,
  hasproRataLeave: false,
  description: 'Central up',
  refTable: 'Ireland',
  refTableId: 56964,
  companyId: 60669,
  status: 'Malta',
  lastModified: dayjs('2023-02-08T12:38'),
  lastModifiedBy: 'sensor Rubber',
};

export const sampleWithNewData: NewLeavePolicy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
