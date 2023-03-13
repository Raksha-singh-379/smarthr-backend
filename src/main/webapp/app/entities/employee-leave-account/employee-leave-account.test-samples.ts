import dayjs from 'dayjs/esm';

import { IEmployeeLeaveAccount, NewEmployeeLeaveAccount } from './employee-leave-account.model';

export const sampleWithRequiredData: IEmployeeLeaveAccount = {
  id: 61021,
};

export const sampleWithPartialData: IEmployeeLeaveAccount = {
  id: 24003,
  creditedLeaves: 34846,
  date: dayjs('2023-03-12T10:44'),
  balance: 79472,
  lastModified: dayjs('2023-03-12T16:02'),
};

export const sampleWithFullData: IEmployeeLeaveAccount = {
  id: 31014,
  carriedLeaves: 76586,
  creditedLeaves: 62422,
  date: dayjs('2023-03-12T18:26'),
  balance: 36275,
  companyId: 13787,
  status: 'interfaces SCSI haptic',
  lastModified: dayjs('2023-03-13T05:35'),
  lastModifiedBy: 'hack gold',
};

export const sampleWithNewData: NewEmployeeLeaveAccount = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
