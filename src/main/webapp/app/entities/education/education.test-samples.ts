import dayjs from 'dayjs/esm';

import { IEducation, NewEducation } from './education.model';

export const sampleWithRequiredData: IEducation = {
  id: 81904,
};

export const sampleWithPartialData: IEducation = {
  id: 93866,
  institution: 'Security',
  endDate: dayjs('2023-02-08T06:25'),
  description: 'Car Managed',
  companyId: 21289,
};

export const sampleWithFullData: IEducation = {
  id: 17940,
  institution: 'whiteboard Licensed leverage',
  subject: 'Profit-focused',
  startDate: dayjs('2023-02-09T03:14'),
  endDate: dayjs('2023-02-09T03:18'),
  educationType: 'Future',
  grade: 'Assistant Gloves',
  description: 'Director',
  lastModified: dayjs('2023-02-09T02:45'),
  lastModifiedBy: 'Money Steel Producer',
  status: 'Creative',
  employeeId: 73880,
  companyId: 60018,
};

export const sampleWithNewData: NewEducation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
