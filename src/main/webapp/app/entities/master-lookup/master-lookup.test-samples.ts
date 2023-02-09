import dayjs from 'dayjs/esm';

import { IMasterLookup, NewMasterLookup } from './master-lookup.model';

export const sampleWithRequiredData: IMasterLookup = {
  id: 63271,
};

export const sampleWithPartialData: IMasterLookup = {
  id: 70596,
  value: 'Personal',
  description: 'niches Product',
  type: 'edge',
  status: 'content-based communities Intelligent',
};

export const sampleWithFullData: IMasterLookup = {
  id: 29171,
  name: 'white Wooden',
  value: 'Rubber',
  description: 'transmitting',
  type: 'index index',
  lastModified: dayjs('2023-02-08T19:11'),
  lastModifiedBy: 'Concrete',
  status: 'Practical Money Creative',
  companyId: 38441,
};

export const sampleWithNewData: NewMasterLookup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
