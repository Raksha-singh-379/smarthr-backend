import dayjs from 'dayjs/esm';

import { ILeavePolicy, NewLeavePolicy } from './leave-policy.model';

export const sampleWithRequiredData: ILeavePolicy = {
  id: 78331,
};

export const sampleWithPartialData: ILeavePolicy = {
  id: 10789,
  totalLeave: 'Plaza Corporate',
  maxLeave: 'Alabama',
  hasproRataLeave: true,
  lastModified: dayjs('2023-02-08T15:11'),
  lastModifiedBy: 'turquoise',
};

export const sampleWithFullData: ILeavePolicy = {
  id: 32211,
  leaveType: 'FTP Cambridgeshire optical',
  isCarryForword: true,
  employeeType: 'Ireland',
  genderLeave: 'Account Montenegro',
  leaveStatus: 'sensor Rubber',
  totalLeave: 'Electronics Rustic',
  maxLeave: 'Handmade',
  hasproRataLeave: false,
  description: 'Plastic',
  lastModified: dayjs('2023-02-08T07:33'),
  lastModifiedBy: 'Tools Agent panel',
  status: 'Berkshire',
  companyId: 56778,
};

export const sampleWithNewData: NewLeavePolicy = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
