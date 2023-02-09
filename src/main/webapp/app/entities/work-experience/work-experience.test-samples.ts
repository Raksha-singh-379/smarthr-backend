import dayjs from 'dayjs/esm';

import { IWorkExperience, NewWorkExperience } from './work-experience.model';

export const sampleWithRequiredData: IWorkExperience = {
  id: 92187,
};

export const sampleWithPartialData: IWorkExperience = {
  id: 76299,
  jobTitle: 'Legacy Communications Orchestrator',
  companyName: 'Horizontal grid-enabled proactive',
  yearOfExp: 70973,
};

export const sampleWithFullData: IWorkExperience = {
  id: 53098,
  jobTitle: 'Human Tactics Officer',
  companyName: 'multi-tasking',
  startDate: dayjs('2023-02-08T06:11'),
  endDate: dayjs('2023-02-08T16:31'),
  lastModified: dayjs('2023-02-08T18:25'),
  lastModifiedBy: 'Afghani calculate matrix',
  status: 'Direct BEAC payment',
  addressId: 76651,
  employeeId: 48687,
  companyId: 10956,
  yearOfExp: 73310,
  jobDesc: 'sensor Account Shirt',
};

export const sampleWithNewData: NewWorkExperience = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
