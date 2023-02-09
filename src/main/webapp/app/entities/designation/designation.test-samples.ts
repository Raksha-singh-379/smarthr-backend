import dayjs from 'dayjs/esm';

import { IDesignation, NewDesignation } from './designation.model';

export const sampleWithRequiredData: IDesignation = {
  id: 18278,
};

export const sampleWithPartialData: IDesignation = {
  id: 20362,
  name: 'contingency Officer',
  lastModifiedBy: 'content-based Infrastructure',
  departmentId: 18407,
};

export const sampleWithFullData: IDesignation = {
  id: 28375,
  name: 'architect THX',
  lastModified: dayjs('2023-02-08T16:35'),
  lastModifiedBy: 'generate connect',
  status: 'invoice',
  departmentId: 97137,
  companyId: 63759,
};

export const sampleWithNewData: NewDesignation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
