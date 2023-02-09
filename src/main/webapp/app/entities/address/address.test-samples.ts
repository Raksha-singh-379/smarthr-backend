import dayjs from 'dayjs/esm';

import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 88754,
};

export const sampleWithPartialData: IAddress = {
  id: 34407,
  type: 'efficient Montana',
  line1: 'interfaces Intelligent Ameliorated',
  state: 'open-source Car hybrid',
  city: 'Kingsport',
  pincode: 'Swiss',
  defaultAdd: true,
  longitude: 33699,
  latitude: 36601,
  status: 'Soap synthesizing intranet',
  refTableType: 'hack',
  refTableId: 76316,
  companyId: 35347,
};

export const sampleWithFullData: IAddress = {
  id: 37391,
  type: 'Birr experiences Re-contextualized',
  line1: 'Compatible black maximize',
  line2: 'solution Plaza',
  country: 'Sao Tome and Principe',
  state: 'Investment virtual',
  city: 'Harveyhaven',
  pincode: 'experiences Books',
  defaultAdd: false,
  landMark: 'microchip Territories',
  longitude: 19987,
  latitude: 67691,
  lastModified: dayjs('2023-02-09T03:48'),
  lastModifiedBy: 'Awesome Mouse red',
  status: 'solid',
  refTableType: 'Practical',
  refTableId: 86605,
  companyId: 17953,
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
