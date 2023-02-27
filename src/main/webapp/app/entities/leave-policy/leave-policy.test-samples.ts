import dayjs from 'dayjs/esm';

import { ILeavePolicy, NewLeavePolicy } from './leave-policy.model';

export const sampleWithRequiredData: ILeavePolicy = {
  id: 78331,
};

export const sampleWithPartialData: ILeavePolicy = {
  id: 51664,
  maxLeave: 'Account Ball',
  hasproRataLeave: true,
  description: 'quantifying',
  status: 'turquoise',
  lastModified: dayjs('2023-02-08T21:04'),
};

export const sampleWithFullData: ILeavePolicy = {
  id: 90670,
  leaveType: 'intuitive Central up',
  isCarryForword: false,
  employeeType: 'partnerships Account',
  genderLeave: 'Card sensor',
  totalLeave: 'withdrawal',
  maxLeave: 'indigo',
  hasproRataLeave: false,
  description: 'Gloves Wooden',
  companyId: 14794,
  status: 'e-enable',
  lastModified: dayjs('2023-02-09T04:18'),
  lastModifiedBy: 'Tasty Fish',
  refTable: 'Open-architected interfaces',
  refTableId: 82560,
};

export const sampleWithNewData: NewLeavePolicy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
