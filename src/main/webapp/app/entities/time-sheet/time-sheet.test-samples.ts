import dayjs from 'dayjs/esm';

import { ITimeSheet, NewTimeSheet } from './time-sheet.model';

export const sampleWithRequiredData: ITimeSheet = {
  id: 58870,
};

export const sampleWithPartialData: ITimeSheet = {
  id: 80567,
  checkOut: dayjs('2023-03-13T07:00'),
  companyId: 54738,
  status: 'Marketing Supervisor mission-critical',
};

export const sampleWithFullData: ITimeSheet = {
  id: 99828,
  checkIn: dayjs('2023-03-12T23:19'),
  checkOut: dayjs('2023-03-12T18:47'),
  date: dayjs('2023-03-13T06:50'),
  hasCheckedIn: false,
  companyId: 41651,
  status: 'Baby Tasty',
  lastModified: dayjs('2023-03-12T16:29'),
  lastModifiedBy: 'archive Path ADP',
};

export const sampleWithNewData: NewTimeSheet = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
