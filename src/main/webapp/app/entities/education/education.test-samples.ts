import dayjs from 'dayjs/esm';

import { IEducation, NewEducation } from './education.model';

export const sampleWithRequiredData: IEducation = {
  id: 81904,
};

export const sampleWithPartialData: IEducation = {
  id: 93866,
  institution: 'Security',
  endDate: dayjs('2023-02-08T06:27'),
  description: 'Car Managed',
  lastModifiedBy: 'Fish',
};

export const sampleWithFullData: IEducation = {
  id: 67006,
  institution: 'Licensed',
  subject: 'Mississippi',
  startYear: dayjs('2023-02-08T22:39'),
  endDate: dayjs('2023-02-08T13:25'),
  educationType: 'Ergonomic',
  grade: 'Wyoming panel',
  description: 'utilisation Computer',
  employeeId: 62677,
  companyId: 34741,
  status: 'Steel',
  lastModified: dayjs('2023-02-08T05:07'),
  lastModifiedBy: 'Movies programming Checking',
};

export const sampleWithNewData: NewEducation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
