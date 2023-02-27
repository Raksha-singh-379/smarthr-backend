import dayjs from 'dayjs/esm';

import { IWorkExperience, NewWorkExperience } from './work-experience.model';

export const sampleWithRequiredData: IWorkExperience = {
  id: 92187,
};

export const sampleWithPartialData: IWorkExperience = {
  id: 10295,
  jobTitle: 'Legacy Assurance Orchestrator',
  companyName: 'overriding France',
  lastModifiedBy: 'proactive',
};

export const sampleWithFullData: IWorkExperience = {
  id: 70973,
  jobTitle: 'Global Interactions Director',
  companyName: 'Computer',
  startDate: dayjs('2023-02-08T16:31'),
  endDate: dayjs('2023-02-08T18:25'),
  addressId: 95881,
  employeeId: 69234,
  companyId: 907,
  jobDesc: 'calculate matrix Chief',
  status: 'BEAC payment connect',
  lastModified: dayjs('2023-02-08T11:12'),
  lastModifiedBy: 'sensor Account Shirt',
};

export const sampleWithNewData: NewWorkExperience = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
