import dayjs from 'dayjs/esm';

import { IEsiDetails, NewEsiDetails } from './esi-details.model';

export const sampleWithRequiredData: IEsiDetails = {
  id: 61856,
};

export const sampleWithPartialData: IEsiDetails = {
  id: 32940,
  isEsiContribution: true,
  additionalEsiRate: 'Glens haptic',
  totalEsiRate: 4701,
  employeId: 68028,
  companyId: 18913,
  lastModified: dayjs('2023-02-08T16:47'),
  lastModifiedBy: 'Granite San',
};

export const sampleWithFullData: IEsiDetails = {
  id: 35505,
  isEsiContribution: true,
  esiNumber: 'Facilitator',
  esiRate: 13655,
  additionalEsiRate: 'Factors Avon parse',
  totalEsiRate: 77537,
  employeId: 68568,
  reEnumerationId: 74027,
  companyId: 59409,
  status: 'Georgia reboot Technician',
  lastModified: dayjs('2023-02-08T08:05'),
  lastModifiedBy: 'copy azure',
};

export const sampleWithNewData: NewEsiDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
