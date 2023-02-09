import dayjs from 'dayjs/esm';

import { ITds, NewTds } from './tds.model';

export const sampleWithRequiredData: ITds = {
  id: 68530,
};

export const sampleWithPartialData: ITds = {
  id: 88582,
  salaryFrom: dayjs('2023-02-09T04:31'),
  percentage: 80386,
  lastModifiedBy: 'COM Concrete Mews',
  status: 'synthesize',
  salarySettingId: 45065,
};

export const sampleWithFullData: ITds = {
  id: 4811,
  salaryFrom: dayjs('2023-02-08T14:33'),
  salaryTo: dayjs('2023-02-09T04:36'),
  percentage: 5483,
  lastModified: dayjs('2023-02-08T18:10'),
  lastModifiedBy: 'implement',
  status: 'transmit Keyboard',
  salarySettingId: 73903,
  companyId: 77221,
};

export const sampleWithNewData: NewTds = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
