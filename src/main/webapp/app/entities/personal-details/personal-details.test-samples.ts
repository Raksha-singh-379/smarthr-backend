import dayjs from 'dayjs/esm';

import { IPersonalDetails, NewPersonalDetails } from './personal-details.model';

export const sampleWithRequiredData: IPersonalDetails = {
  id: 97979,
};

export const sampleWithPartialData: IPersonalDetails = {
  id: 30080,
  passportExpDate: dayjs('2023-02-08T17:54'),
  maritalStatus: 'Streamlined',
  religion: 'Executive secured withdrawal',
  isSpousEmployed: false,
  lastModified: dayjs('2023-02-09T01:01'),
  lastModifiedBy: 'Account enterprise Greece',
  status: 'Towels',
  employeeId: 65856,
  companyId: 15657,
  personalIdNo: 'Latvian',
};

export const sampleWithFullData: IPersonalDetails = {
  id: 81379,
  passportNo: 'Rwanda open-source Fresh',
  passportExpDate: dayjs('2023-02-08T08:29'),
  telephoneNo: 'bandwidth',
  nationality: 'New Connecticut Barbados',
  maritalStatus: 'quantify Small',
  religion: 'Hat',
  isSpousEmployed: true,
  lastModified: dayjs('2023-02-08T19:26'),
  lastModifiedBy: 'Computers',
  status: 'RSS',
  employeeId: 73335,
  companyId: 25059,
  personalIdNo: 'Functionality',
  bloodGroup: 'Baby clicks-and-mortar',
  dateOfBirth: dayjs('2023-02-09'),
};

export const sampleWithNewData: NewPersonalDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
