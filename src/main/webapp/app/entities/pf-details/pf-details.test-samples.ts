import dayjs from 'dayjs/esm';

import { IPfDetails, NewPfDetails } from './pf-details.model';

export const sampleWithRequiredData: IPfDetails = {
  id: 67438,
};

export const sampleWithPartialData: IPfDetails = {
  id: 78480,
  pfNumber: 'District Bike',
  totalPfRate: 13788,
  lastModifiedBy: 'mobile Programmable Books',
  status: 'deposit',
  employeId: 79900,
  reEnumerationId: 30816,
};

export const sampleWithFullData: IPfDetails = {
  id: 41679,
  isPfContribution: false,
  pfNumber: 'Iowa',
  pfRate: 45228,
  additionalPfRate: 'Avon Jersey Computers',
  totalPfRate: 48735,
  lastModified: dayjs('2023-02-08T13:02'),
  lastModifiedBy: 'Metrics',
  status: 'deposit e-services',
  employeId: 11023,
  reEnumerationId: 95537,
};

export const sampleWithNewData: NewPfDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
