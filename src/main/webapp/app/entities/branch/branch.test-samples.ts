import dayjs from 'dayjs/esm';

import { IBranch, NewBranch } from './branch.model';

export const sampleWithRequiredData: IBranch = {
  id: 244,
  branchName: 'blue hack Intelligent',
};

export const sampleWithPartialData: IBranch = {
  id: 11378,
  branchName: 'superstructure Handcrafted',
  description: 'Denar Human Architect',
  regionId: 66061,
  lastModified: dayjs('2023-02-09T00:53'),
  lastModifiedBy: 'wireless cultivate',
};

export const sampleWithFullData: IBranch = {
  id: 2111,
  branchName: 'Seamless microchip',
  description: 'Point integrated',
  branchcode: 'Colorado Sum world-class',
  branchType: 'demand-driven transparent Plastic',
  webSite: 'turn-key monitor',
  branchId: 89228,
  regionId: 2440,
  companyId: 47603,
  status: 'Polarised Bike',
  lastModified: dayjs('2023-02-08T12:38'),
  lastModifiedBy: 'Florida Configuration International',
};

export const sampleWithNewData: NewBranch = {
  branchName: 'calculate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
