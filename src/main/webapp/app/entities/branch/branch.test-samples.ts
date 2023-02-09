import dayjs from 'dayjs/esm';

import { IBranch, NewBranch } from './branch.model';

export const sampleWithRequiredData: IBranch = {
  id: 244,
  branchName: 'blue hack Intelligent',
};

export const sampleWithPartialData: IBranch = {
  id: 80449,
  branchName: 'e-services',
  description: 'Handcrafted International Triple-buffered',
  lastModified: dayjs('2023-02-08T06:06'),
  companyId: 98544,
};

export const sampleWithFullData: IBranch = {
  id: 45591,
  branchName: 'deposit',
  description: 'matrix Quality Ecuador',
  branchcode: 'microchip Berkshire',
  branchType: 'integrated multi-byte',
  webSite: 'Sum world-class Response',
  status: 'transparent Plastic action-items',
  lastModified: dayjs('2023-02-08T05:08'),
  lastModifiedBy: 'protocol Health Polarised',
  regionId: 18014,
  companyId: 22917,
};

export const sampleWithNewData: NewBranch = {
  branchName: 'Palladium Car',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
