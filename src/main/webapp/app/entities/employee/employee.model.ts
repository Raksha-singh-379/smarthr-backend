import dayjs from 'dayjs/esm';

export interface IEmployee {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  username?: string | null;
  password?: string | null;
  department?: string | null;
  designation?: string | null;
  gender?: string | null;
  empUniqueId?: string | null;
  joindate?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
  branchId?: number | null;
  regionId?: number | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
