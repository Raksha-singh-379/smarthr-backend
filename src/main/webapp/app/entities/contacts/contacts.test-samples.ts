import dayjs from 'dayjs/esm';

import { IContacts, NewContacts } from './contacts.model';

export const sampleWithRequiredData: IContacts = {
  id: 22084,
};

export const sampleWithPartialData: IContacts = {
  id: 9986,
  contactPref: 'National Salad',
  contactType: 'Saudi Communications',
  lastModifiedBy: 'Balboa Buckinghamshire',
  status: 'visionary',
  refTableId: 61074,
};

export const sampleWithFullData: IContacts = {
  id: 90126,
  name: 'Leu',
  contactPref: 'copying',
  contactType: 'Strategist Neck Polarised',
  contact: 'hacking',
  lastModified: dayjs('2023-02-08T17:17'),
  lastModifiedBy: 'Intelligent',
  status: 'Ergonomic syndicate Cheese',
  refTableType: 'Steel Outdoors',
  refTableId: 77280,
  companyId: 25290,
};

export const sampleWithNewData: NewContacts = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
