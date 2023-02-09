import dayjs from 'dayjs/esm';

export interface IPersonalDetails {
  id: number;
  passportNo?: string | null;
  passportExpDate?: dayjs.Dayjs | null;
  telephoneNo?: string | null;
  nationality?: string | null;
  maritalStatus?: string | null;
  religion?: string | null;
  isSpousEmployed?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  employeeId?: number | null;
  companyId?: number | null;
  personalIdNo?: string | null;
  bloodGroup?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
}

export type NewPersonalDetails = Omit<IPersonalDetails, 'id'> & { id: null };
