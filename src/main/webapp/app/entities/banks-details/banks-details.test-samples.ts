import dayjs from 'dayjs/esm';

import { IBanksDetails, NewBanksDetails } from './banks-details.model';

export const sampleWithRequiredData: IBanksDetails = {
  id: 92491,
};

export const sampleWithPartialData: IBanksDetails = {
  id: 68068,
  bankName: 'Extended parallelism',
  branchTransCode: 'PNG',
  taxNumber: 'Computer',
  branchName: 'Vatu',
  lastModified: dayjs('2023-02-08T09:15'),
  refTableType: 'Minnesota microchip Group',
};

export const sampleWithFullData: IBanksDetails = {
  id: 21816,
  accountNumber: 16209,
  bankName: 'Brand Dynamic',
  branchTransCode: 'Account Mountains',
  taxNumber: 'SMTP',
  branchName: 'Verde Soap',
  lastModified: dayjs('2023-02-08T11:07'),
  lastModifiedBy: 'synergies indexing',
  status: 'USB channels',
  refTableType: 'Brand Carolina',
  refTableId: 58076,
  companyId: 47060,
};

export const sampleWithNewData: NewBanksDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
