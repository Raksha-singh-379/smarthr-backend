import dayjs from 'dayjs/esm';

import { IWorkingHours, NewWorkingHours } from './working-hours.model';

export const sampleWithRequiredData: IWorkingHours = {
  id: 65185,
};

export const sampleWithPartialData: IWorkingHours = {
  id: 53714,
  noOfHours: 'violet Triple-buffered',
  employmentTypeId: 13705,
  status: 'protocol',
  refTable: 'navigate',
};

export const sampleWithFullData: IWorkingHours = {
  id: 86639,
  noOfHours: 'alliance',
  employmentTypeId: 1639,
  companyId: 60028,
  status: 'grey Account',
  lastModified: dayjs('2023-03-13T00:13'),
  lastModifiedBy: 'invoice Soft',
  refTable: 'Self-enabling interface project',
  refTableId: 63676,
};

export const sampleWithNewData: NewWorkingHours = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
