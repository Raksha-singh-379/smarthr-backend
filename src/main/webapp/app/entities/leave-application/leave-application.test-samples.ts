import dayjs from 'dayjs/esm';

import { ILeaveApplication, NewLeaveApplication } from './leave-application.model';

export const sampleWithRequiredData: ILeaveApplication = {
  id: 91940,
};

export const sampleWithPartialData: ILeaveApplication = {
  id: 48531,
  noOfDays: 95755,
  reason: 'Shoes',
  toDate: dayjs('2023-02-08T10:50'),
  leaveStatus: 'Handcrafted Bangladesh',
  lastModifiedBy: 'executive',
  status: 'Macao distributed',
  employeId: 13284,
  companyId: 25221,
};

export const sampleWithFullData: ILeaveApplication = {
  id: 1506,
  leaveType: 'ADP',
  balanceLeave: 3353,
  noOfDays: 26196,
  reason: 'CSS',
  year: 1870,
  formDate: dayjs('2023-02-08T19:19'),
  toDate: dayjs('2023-02-08T10:16'),
  leaveStatus: 'access Rustic',
  lastModified: dayjs('2023-02-08T07:35'),
  lastModifiedBy: 'innovative embrace GB',
  status: 'Avon navigating Kentucky',
  employeId: 30305,
  companyId: 44734,
};

export const sampleWithNewData: NewLeaveApplication = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
