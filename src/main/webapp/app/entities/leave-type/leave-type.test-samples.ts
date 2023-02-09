import dayjs from 'dayjs/esm';

import { ILeaveType, NewLeaveType } from './leave-type.model';

export const sampleWithRequiredData: ILeaveType = {
  id: 21210,
};

export const sampleWithPartialData: ILeaveType = {
  id: 75823,
  noOfDays: 'Automotive bluetooth',
  lastModified: dayjs('2023-02-08T08:46'),
  status: 'Borders Industrial Operations',
};

export const sampleWithFullData: ILeaveType = {
  id: 9737,
  leaveType: 'Movies',
  noOfDays: 'Solutions',
  recordStatus: 'violet optical compress',
  lastModified: dayjs('2023-02-08T10:16'),
  lastModifiedBy: 'Unbranded',
  status: 'Borders',
  companyId: 62435,
};

export const sampleWithNewData: NewLeaveType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
