import dayjs from 'dayjs/esm';

import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  id: 27363,
  regionName: 'back-end Practical',
};

export const sampleWithPartialData: IRegion = {
  id: 75657,
  regionName: 'parse',
  description: 'Berkshire',
  lastModifiedBy: 'deposit salmon Product',
  companyId: 32445,
};

export const sampleWithFullData: IRegion = {
  id: 12369,
  regionName: 'Senior Administrator Grocery',
  description: 'ROI',
  status: 'Shoes overriding',
  lastModified: dayjs('2023-02-08T22:07'),
  lastModifiedBy: 'Iowa Rubber',
  companyId: 21816,
};

export const sampleWithNewData: NewRegion = {
  regionName: 'Rubber',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
