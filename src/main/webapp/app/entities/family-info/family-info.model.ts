import dayjs from 'dayjs/esm';

export interface IFamilyInfo {
  id: number;
  name?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  relation?: string | null;
  address?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  employeeId?: number | null;
  companyId?: number | null;
}

export type NewFamilyInfo = Omit<IFamilyInfo, 'id'> & { id: null };
