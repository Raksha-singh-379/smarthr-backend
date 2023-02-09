import dayjs from 'dayjs/esm';

import { ISalarySettings, NewSalarySettings } from './salary-settings.model';

export const sampleWithRequiredData: ISalarySettings = {
  id: 18645,
};

export const sampleWithPartialData: ISalarySettings = {
  id: 82287,
  da: 17493,
  salaryFrom: dayjs('2023-02-08T07:08'),
  lastModified: dayjs('2023-02-08T05:44'),
  status: 'Account TCP',
  companyId: 29332,
};

export const sampleWithFullData: ISalarySettings = {
  id: 88072,
  da: 99753,
  hra: 93136,
  employeeShare: 35194,
  companyShare: 80054,
  salaryFrom: dayjs('2023-02-08T09:09'),
  salaryTo: dayjs('2023-02-08T06:22'),
  lastModified: dayjs('2023-02-09T02:24'),
  lastModifiedBy: 'Dominican well-modulated',
  status: 'synthesizing Automotive',
  companyId: 67147,
};

export const sampleWithNewData: NewSalarySettings = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
