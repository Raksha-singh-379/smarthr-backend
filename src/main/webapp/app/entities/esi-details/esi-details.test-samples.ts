import dayjs from 'dayjs/esm';

import { IEsiDetails, NewEsiDetails } from './esi-details.model';

export const sampleWithRequiredData: IEsiDetails = {
  id: 61856,
};

export const sampleWithPartialData: IEsiDetails = {
  id: 58776,
  isEsiContribution: false,
  additionalEsiRate: 'Minnesota Buckinghamshire Small',
  totalEsiRate: 18913,
  lastModified: dayjs('2023-02-08T16:46'),
  status: 'Granite San',
  reEnumerationId: 35505,
};

export const sampleWithFullData: IEsiDetails = {
  id: 80590,
  isEsiContribution: false,
  esiNumber: 'calculating state sexy',
  esiRate: 91753,
  additionalEsiRate: 'Mobility Shilling COM',
  totalEsiRate: 50245,
  lastModified: dayjs('2023-02-08T10:47'),
  lastModifiedBy: 'silver standardization copy',
  status: 'Creative',
  employeId: 7848,
  reEnumerationId: 34717,
};

export const sampleWithNewData: NewEsiDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
