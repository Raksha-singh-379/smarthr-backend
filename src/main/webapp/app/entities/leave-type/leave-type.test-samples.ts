import dayjs from 'dayjs/esm';

import { ILeaveType, NewLeaveType } from './leave-type.model';

export const sampleWithRequiredData: ILeaveType = {
  id: 21210,
};

export const sampleWithPartialData: ILeaveType = {
  id: 3615,
  noOfDays: 91161,
  hasEarned: true,
  companyId: 80351,
  lastModified: dayjs('2023-02-08T13:28'),
  lastModifiedBy: 'programming invoice',
};

export const sampleWithFullData: ILeaveType = {
  id: 98925,
  leaveType: 'Operations Small',
  noOfDays: 8300,
  hasCarryForward: true,
  hasEarned: false,
  hasCustomPolicy: true,
  companyId: 193,
  status: 'Manager invoice Buckinghamshire',
  lastModified: dayjs('2023-02-08T06:37'),
  lastModifiedBy: 'analyzer online Granite',
};

export const sampleWithNewData: NewLeaveType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
