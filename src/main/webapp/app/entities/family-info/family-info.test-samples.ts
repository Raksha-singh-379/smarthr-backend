import dayjs from 'dayjs/esm';

import { IFamilyInfo, NewFamilyInfo } from './family-info.model';

export const sampleWithRequiredData: IFamilyInfo = {
  id: 35605,
};

export const sampleWithPartialData: IFamilyInfo = {
  id: 35314,
  status: 'Pre-emptive array',
  employeeId: 5228,
  companyId: 87433,
};

export const sampleWithFullData: IFamilyInfo = {
  id: 26694,
  name: 'bypass compressing copying',
  dateOfBirth: dayjs('2023-02-08'),
  relation: 'Maine Synergized',
  address: 'e-services Bacon Toys',
  lastModified: dayjs('2023-02-09T01:15'),
  lastModifiedBy: 'De-engineered Account circuit',
  status: 'transmit Intranet',
  employeeId: 22196,
  companyId: 82933,
};

export const sampleWithNewData: NewFamilyInfo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
