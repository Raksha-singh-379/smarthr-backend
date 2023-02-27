import dayjs from 'dayjs/esm';

import { ILeaveApplication, NewLeaveApplication } from './leave-application.model';

export const sampleWithRequiredData: ILeaveApplication = {
  id: 91940,
};

export const sampleWithPartialData: ILeaveApplication = {
  id: 52122,
  reason: 'Implementation Global',
  year: 94322,
  leaveStatus: 'Benin Infrastructure Ergonomic',
  status: 'Macao distributed',
  companyId: 13284,
  lastModified: dayjs('2023-02-08T22:45'),
  lastModifiedBy: 'strategy',
};

export const sampleWithFullData: ILeaveApplication = {
  id: 14469,
  leaveType: 'demand-driven',
  noOfDays: 17360,
  reason: 'Tools Greece Movies',
  year: 37074,
  formDate: dayjs('2023-02-08T07:37'),
  toDate: dayjs('2023-02-08T12:18'),
  leaveStatus: 'Fresh Rubber',
  status: 'scale',
  employeId: 1263,
  companyId: 57564,
  lastModified: dayjs('2023-02-08T08:40'),
  lastModifiedBy: 'knowledge Product',
};

export const sampleWithNewData: NewLeaveApplication = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
