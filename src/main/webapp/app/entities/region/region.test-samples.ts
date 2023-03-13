import dayjs from 'dayjs/esm';

import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  id: 27363,
  regionName: 'back-end Practical',
};

export const sampleWithPartialData: IRegion = {
  id: 12726,
  regionName: 'Strategist Berkshire quantifying',
  description: 'salmon Product',
  status: 'Soft',
  lastModified: dayjs('2023-02-09T02:59'),
  lastModifiedBy: 'Administrator Grocery',
};

export const sampleWithFullData: IRegion = {
  id: 23487,
  regionName: 'Horizontal Shoes',
  description: 'turquoise mesh Executive',
  regionId: 8297,
  companyId: 21816,
  status: 'Rubber',
  lastModified: dayjs('2023-02-08T17:38'),
  lastModifiedBy: 'deposit Berkshire',
};

export const sampleWithNewData: NewRegion = {
  regionName: 'array Refined Ergonomic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
